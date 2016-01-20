package com.handu.open.dubbo.monitor.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.AppItem;

@Service
public class AppItemService {
	private static Logger logger = Logger.getLogger(AppItemService.class);
	public static final String CLASSNAME = AppItemService.class.getName() + ".";
	@Autowired
	private DubboInvokeBaseDAO dubboInvokeDAO;

	public AppItem getById(long id) {
		AppItem param = new AppItem();
		param.setId(id);
		AppItem dbObj = dubboInvokeDAO.get(CLASSNAME, "findEntity", param);
		return dbObj;
	}

	public AppItem getByName(String name) {
		if(StringUtils.isBlank(name)){
			logger.error("查询name参数不能为空！");
			throw new RuntimeException("查询name参数不能为空！");
		}
		AppItem param = new AppItem();
		param.setName(name);
		AppItem dbObj = dubboInvokeDAO.get(CLASSNAME, "findEntity", param);
		return dbObj;
	}

	public List<AppItem> list() {
		List<AppItem> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity");
		return list;
	}

	public List<AppItem> list(AppItem param) {
		List<AppItem> list = dubboInvokeDAO.getList(CLASSNAME, "findEntity", param);
		return list;
	}

	public void add(AppItem obj) {
		dubboInvokeDAO.insert(CLASSNAME, "addEntity", obj);
	}

	public void update(AppItem obj) {
		dubboInvokeDAO.update(CLASSNAME, "updateEntity", obj);
	}

	public void delete(long id) {
		dubboInvokeDAO.delete(CLASSNAME, "deleteEntity", id);
	}
}
