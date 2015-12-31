package com.handu.open.dubbo.monitor.service;

	
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.monitor.MonitorService;
import com.handu.open.dubbo.monitor.dao.ConfigDAO;
import com.handu.open.dubbo.monitor.dao.DayStatsDAO;
import com.handu.open.dubbo.monitor.domain.DayStats;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;

@Component
public class DayStatsService {

	
	private static Logger logger = Logger.getLogger(DayStatsService.class);

	private Map<String, DayStats> map = new HashMap<String, DayStats>();
	long count=0;

	@Autowired
	ConfigDAO configDAO;

	@Autowired
	DayStatsDAO dayStatsDAO;
	// 前提条件：单线程程序
	public void add( DubboInvoke di) {
		
		long serviceId = configDAO.getServiceId( di.getService());
		long methodId = configDAO.getMethodId(serviceId, di.getMethod());

		String key = makeKey(di.getInvokeDate(),  serviceId, methodId);		
		synchronized (map) {
			DayStats ds = map.get(key);
			if (ds == null) {
				ds = new DayStats();
				ds.setInvokeDate(di.getInvokeDate());				
				ds.setServiceId(serviceId);
				ds.setMethodId(methodId);
				map.put(key, ds);
			}
			addtoStat(di, ds);
		}
	}

	@PostConstruct
	public void start() {
		Thread writeThread = new Thread(new Runnable() {
            public void run() {
            	
            	
                while (true) {
                    try {
                    	saveDataToDisk(); // 记录统计日志
                    } catch (Exception t) { // 防御性容错
                        logger.error("saveDataToDisk  failed , sleep 10分钟, cause: " + t.getMessage(), t);                        
                    }
                    try {
                        Thread.sleep(60000); 
                        //Thread.sleep(600000); 
                    } catch (Throwable t2) {
                    }
                }
            }
        });
        writeThread.setDaemon(true);
        writeThread.setName("write data to database");
        writeThread.start();
	}
	
	private void saveDataToDisk() {
		
		Collection<DayStats> kk = null;
		
		synchronized (map) {
			kk = new ArrayList<DayStats>(map.values());
			map.clear();
		}
		
		for(DayStats ds:kk){		
			dayStatsDAO.saveOrUpdate(ds);

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

	}

	private String makeKey(Date invokeDate, long serviceId,
			long methodId) {

		StringBuilder buf = new StringBuilder();
		buf.append(invokeDate.toString()).append(".")
				.append(serviceId).append(".").append(methodId);

		return buf.toString();
	}

}
