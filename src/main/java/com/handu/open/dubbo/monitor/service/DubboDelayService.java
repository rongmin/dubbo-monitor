package com.handu.open.dubbo.monitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.monitor.MonitorService;
import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceMethodBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.ApplicationServiceMethod;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;

@Service
public class DubboDelayService {
	public static final String CLASSNAME = DubboDelayService.class.getName() + ".";
	@Autowired
	private DubboInvokeBaseDAO dubboInvokeDAO;
	@Autowired
	private ApplicationServiceMethodBaseDAO asmDao;
	@Autowired
	private Environment env;

	public void addSlowElapse(DubboInvoke obj, long elapse) {
		if (obj.getElapsed() > elapse * obj.getSuccess()) {
			dubboInvokeDAO.insert(CLASSNAME, "addEntity", obj);
		}
	}

	public void add(DubboInvoke obj) {
		Long slowElapse = new Long(env.getProperty("monitor.slowElapse", "0"));
		ApplicationServiceMethod asmObj = asmDao.getByServiceAndMethod(obj);
		if (asmObj == null) {// 未定义方法慢查
			if (slowElapse > 0) {// 使用全局定义
				addSlowElapse(obj, slowElapse);
			}
			return;
		}
		if (MonitorService.PROVIDER.equals(obj.getType())) {
			if (asmObj.getMaxtimeProvider() > 0) {
				addSlowElapse(obj, asmObj.getMaxtimeProvider());
			} else if (slowElapse > 0) {
				addSlowElapse(obj, slowElapse);
			}
		} else {
			if (asmObj.getMaxtimeConsumer() > 0) {
				addSlowElapse(obj, asmObj.getMaxtimeConsumer());
			} else if (slowElapse > 0) {
				addSlowElapse(obj, slowElapse);
			}
		}
	}

	public DubboInvoke getById(long id) {
		DubboInvoke param = new DubboInvoke();
		param.setId(id);
		DubboInvoke dbObj = dubboInvokeDAO.get(CLASSNAME, "findEntity", param);
		return dbObj;
	}

	public List<DubboInvoke> list() {
		List<DubboInvoke> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity");
		return list;
	}

	public List<DubboInvoke> list(DubboInvoke param) {
		List<DubboInvoke> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity", param);
		return list;
	}

	public void update(DubboInvoke obj) {
		dubboInvokeDAO.update(CLASSNAME, "updateEntity", obj);
	}

	public void delete(long id) {
		dubboInvokeDAO.delete(CLASSNAME, "deleteEntity", id);
	}
}
