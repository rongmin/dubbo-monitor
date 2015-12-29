package com.handu.open.dubbo.monitor.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.monitor.MonitorService;
import com.handu.open.dubbo.monitor.dao.ConfigDAO;
import com.handu.open.dubbo.monitor.dao.DayStatsDAO;
import com.handu.open.dubbo.monitor.domain.DayStats;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;

@Component
public class DayStatsService {

	private Map<String, DayStats> map = new HashMap<String, DayStats>();
	long count=0;

	@Autowired
	ConfigDAO configDAO;

	@Autowired
	DayStatsDAO dayStatsDAO;
	// 前提条件：单线程程序
	public void add(String appName, DubboInvoke di) {
		long appId = configDAO.getAppId(appName);
		long serviceId = configDAO.getServiceId(appId, di.getService());
		long methodId = configDAO.getMethodId(serviceId, di.getMethod());

		String key = makeKey(di.getInvokeDate(), appId, serviceId, methodId);

		DayStats ds = map.get(key);
		if (ds == null) {
			ds = new DayStats();
			ds.setInvokeDate(di.getInvokeDate());
			ds.setAppId(appId);
			ds.setServiceId(serviceId);
			ds.setMethodId(methodId);
			map.put(key, ds);
		}

		addtoStat(di, ds);
		
		if(count++%10==0)
			saveDataToDisk();

	}

	private void saveDataToDisk() {
		Collection<DayStats> coll = map.values();
		for(DayStats ds:coll){
			if(ds.isChanged()){
				dayStatsDAO.saveOrUpdate(ds);
				ds.setChanged(false);
			}
		}
		
	}

	private void addtoStat(DubboInvoke di, DayStats ds) {
		if(di.getType().equals(MonitorService.PROVIDER)){
			ds.setSuccessProvider(ds.getSuccessProvider()+di.getSuccess());
			ds.setFailureProvider(ds.getFailureProvider()+di.getFailure());
			ds.setElapsedProvider(ds.getElapsedProvider()+di.getElapsed());			
		}else{
			ds.setSuccessConsumer(ds.getSuccessConsumer()+di.getSuccess());
			ds.setFailureConsumer(ds.getFailureConsumer()+di.getFailure());
			ds.setElapsedConsumer(ds.getElapsedConsumer()+di.getElapsed());
		}
		ds.setChanged(true);
		
	}

	private String makeKey(Date invokeDate, long appId, long serviceId,
			long methodId) {

		StringBuilder buf = new StringBuilder();
		buf.append(invokeDate.toString()).append(".").append(appId).append(".")
				.append(serviceId).append(".").append(methodId);

		return buf.toString();
	}

}
