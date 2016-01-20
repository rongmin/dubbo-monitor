package com.handu.open.dubbo.monitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.AppAlarm;

@Service
public class AppAlarmService {
	public static final String CLASSNAME = AppAlarmService.class.getName() + ".";
	@Autowired
	private DubboInvokeBaseDAO dubboInvokeDAO;

	public AppAlarm getById(long id) {
		AppAlarm param = new AppAlarm();
		param.setId(id);
		AppAlarm dbObj = dubboInvokeDAO.get(CLASSNAME, "findEntity", param);
		return dbObj;
	}

	public List<AppAlarm> list() {
		List<AppAlarm> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity");
		return list;
	}

	public List<AppAlarm> list(AppAlarm param) {
		List<AppAlarm> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity", param);
		return list;
	}

	public void add(AppAlarm obj) {
		dubboInvokeDAO.insert(CLASSNAME, "addEntity", obj);
	}

	public void update(AppAlarm obj) {
		dubboInvokeDAO.update(CLASSNAME, "updateEntity", obj);
	}

	public void delete(long id) {
		dubboInvokeDAO.delete(CLASSNAME, "deleteEntity", id);
	}
}
