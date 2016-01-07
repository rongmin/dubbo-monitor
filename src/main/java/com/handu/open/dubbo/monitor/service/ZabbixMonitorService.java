package com.handu.open.dubbo.monitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;
import com.handu.open.dubbo.monitor.domain.ZabbixInvoke;

@Service
public class ZabbixMonitorService {

	public static final String CLASSNAME = ZabbixMonitorService.class.getName() + ".";
	@Autowired
	private DubboInvokeBaseDAO dubboInvokeDAO;

	public void add(ZabbixInvoke zi) {
		dubboInvokeDAO.insert(CLASSNAME, "addZabbixInvoke", zi);
	}

	public void saveOrUpdate(DubboInvoke di) {
		ZabbixInvoke zi = new ZabbixInvoke();
		zi.setService(di.getService());
		zi.setMethod(di.getMethod());
		zi.setProvider(di.getProvider());
		List<ZabbixInvoke> list = dubboInvokeDAO.getList(CLASSNAME, "getZabbixInvoke", zi);
		if (list != null && list.size() > 0) {
			// update
			ZabbixInvoke existObj = list.get(0);
			existObj.setInvokeTime(di.getInvokeTime());
			existObj.setSuccess(di.getSuccess());
			existObj.setFailure(di.getFailure());
			existObj.setElapsed(di.getElapsed());
			existObj.setConcurrent(di.getConcurrent());
			existObj.setMaxElapsed(di.getMaxElapsed());
			existObj.setMaxConcurrent(di.getMaxConcurrent());
			update(existObj);
		} else {
			// add
			zi.setInvokeTime(di.getInvokeTime());
			zi.setSuccess(di.getSuccess());
			zi.setFailure(di.getFailure());
			zi.setElapsed(di.getElapsed());
			zi.setConcurrent(di.getConcurrent());
			zi.setMaxElapsed(di.getMaxElapsed());
			zi.setMaxConcurrent(di.getMaxConcurrent());
			add(zi);
		}
	}

	public void update(ZabbixInvoke zi) {
		dubboInvokeDAO.update(CLASSNAME, "updateZabbixInvoke", zi);
	}
}
