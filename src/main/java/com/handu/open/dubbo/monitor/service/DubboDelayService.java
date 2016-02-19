package com.handu.open.dubbo.monitor.service;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.monitor.MonitorService;
import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceMethodBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.ApplicationService;
import com.handu.open.dubbo.monitor.domain.ApplicationServiceMethod;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;

@Service
public class DubboDelayService {
	private static Logger logger = Logger.getLogger(DubboDelayService.class);
	public static final String CLASSNAME = DubboDelayService.class.getName() + ".";
	@Autowired
	private DubboInvokeBaseDAO dubboInvokeDAO;
	@Autowired
	private ApplicationServiceMethodBaseDAO asmDao;
	
	@Autowired
	private DelayStatusHandler delayStatusHandler;
	@Autowired
	private Environment env;

	public void addSlowElapse(DubboDelay obj, long elapse) {
		if (obj.getElapsed() > elapse * obj.getSuccess()) {
			dubboInvokeDAO.insert(CLASSNAME, "addEntity", obj);
			delayStatusHandler.add(obj);
		}
	}

	public void add(DubboInvoke obj) {
		Long slowElapse = new Long(env.getProperty("monitor.slowElapse", "0"));
		ApplicationServiceMethod asmObj = asmDao.getByServiceAndMethod(obj);
		if (asmObj == null) {// 未定义方法慢查
			logger.warn("undefined method slow elapse.");
			return;
		}
		DubboDelay dubboDelay = new DubboDelay();
		BeanUtils.copyProperties(obj, dubboDelay);
		dubboDelay.setServiceId(asmObj.getServiceId());
		dubboDelay.setMethodId(asmObj.getId());
		if (MonitorService.PROVIDER.equals(obj.getType())) {
			if (asmObj.getMaxtimeProvider() > 0) {
				addSlowElapse(dubboDelay, asmObj.getMaxtimeProvider());
			} else if (slowElapse > 0) {
				addSlowElapse(dubboDelay, slowElapse);
			}
		} else {
			if (asmObj.getMaxtimeConsumer() > 0) {
				addSlowElapse(dubboDelay, asmObj.getMaxtimeConsumer());
			} else if (slowElapse > 0) {
				addSlowElapse(dubboDelay, slowElapse);
			}
		}
	}

	public DubboDelay getById(long id) {
		DubboDelay param = new DubboDelay();
		param.setId(id);
		DubboDelay dbObj = dubboInvokeDAO.get(CLASSNAME, "findEntity", param);
		return dbObj;
	}

	public List<ApplicationService> listSlowServices(DubboDelay param) {
		List<ApplicationService> list = dubboInvokeDAO.getList(CLASSNAME, "findSlowServices", param);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public List<ApplicationServiceMethod> getMethodsByService(DubboDelay param) {
		List<ApplicationServiceMethod> list = dubboInvokeDAO.getList(CLASSNAME, "getMethodsByService", param);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	/**
	 * 统计各方法调用信息
	 *
	 * @param dubboInvoke
	 * @return
	 */
	public List<DubboDelay> countDubboDelayInfo(DubboDelay param) {
		if (StringUtils.isEmpty(param.getServiceId()) || StringUtils.isEmpty(param.getMethodId())) {
			logger.error("统计查询缺少必要参数！");
			throw new RuntimeException("统计查询缺少必要参数！");
		}
		List<DubboDelay> list = dubboInvokeDAO.getList(CLASSNAME, "countDubboDelayInfo", param);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	/**
	 * 统计调用数据用于图表展示
	 *
	 * @param param
	 */
	public List<DubboDelay> countDubboDelay(DubboDelay param) {
		if (StringUtils.isEmpty(param.getServiceId())) {
			logger.error("统计查询缺少必要参数！");
			throw new RuntimeException("统计查询缺少必要参数！");
		}
		List<DubboDelay> list = dubboInvokeDAO.getList(CLASSNAME, "countDubboDelay", param);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public List<DubboDelay> list() {
		List<DubboDelay> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity");
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public List<DubboDelay> list(DubboDelay param) {
		List<DubboDelay> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity", param);
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public void update(DubboDelay obj) {
		dubboInvokeDAO.update(CLASSNAME, "updateEntity", obj);
	}

	public void delete(long id) {
		dubboInvokeDAO.delete(CLASSNAME, "deleteEntity", id);
	}
}
