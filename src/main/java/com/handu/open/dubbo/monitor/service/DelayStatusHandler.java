package com.handu.open.dubbo.monitor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.monitor.MonitorService;
import com.handu.open.dubbo.monitor.domain.DayStats;
import com.handu.open.dubbo.monitor.domain.DubboDelay;

@Service
public class DelayStatusHandler {
	private static Logger logger = Logger.getLogger(DelayStatusHandler.class);

	private Map<String, DayStats> map = new HashMap<String, DayStats>();

	@Autowired
	private DelayStatusService delayStatusDAO;

	// 前提条件：单线程程序
	public void add(DubboDelay di) {
		String key = makeKey(di.getInvokeDate(), di.getServiceId(), di.getMethodId());
		synchronized (map) {
			DayStats ds = map.get(key);
			if (ds == null) {
				ds = new DayStats();
				ds.setInvokeDate(di.getInvokeDate());
				ds.setServiceId(di.getServiceId());
				ds.setMethodId(di.getMethodId());
				map.put(key, ds);
			}
			addtoStat(di, ds);
		}
	}

	private void addtoStat(DubboDelay di, DayStats ds) {
		if (di.getType().equals(MonitorService.PROVIDER)) {
			ds.setSuccessProvider(ds.getSuccessProvider() + di.getSuccess());
			ds.setFailureProvider(ds.getFailureProvider() + di.getFailure());
			ds.setElapsedProvider(ds.getElapsedProvider() + di.getElapsed());
		} else {
			ds.setSuccessConsumer(ds.getSuccessConsumer() + di.getSuccess());
			ds.setFailureConsumer(ds.getFailureConsumer() + di.getFailure());
			ds.setElapsedConsumer(ds.getElapsedConsumer() + di.getElapsed());
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
						logger.error("saveDelayStatusToDisk  failed , cause: " + t.getMessage(), t);
					}
					try {
						Thread.sleep(60000);
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

		for (DayStats ds : kk) {
			delayStatusDAO.saveOrUpdate(ds);
		}
	}

	private String makeKey(Date invokeDate, long serviceId, long methodId) {
		StringBuilder buf = new StringBuilder();
		buf.append(invokeDate.toString()).append(".").append(serviceId).append(".").append(methodId);

		return buf.toString();
	}
}
