package com.handu.open.dubbo.monitor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceMethodBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.DubboInvokeBaseDAO;
import com.handu.open.dubbo.monitor.domain.DayStats;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.StatsSum;

@Service
public class DelayStatusService {
	public static final String CLASSNAME = DelayStatusService.class.getName() + ".";

	@Autowired
	private DubboInvokeBaseDAO dubboInvokeBaseDAO;

	@Autowired
	private ApplicationServiceBaseDAO applicationServiceBaseDAO;

	@Autowired
	private ApplicationServiceMethodBaseDAO applicationServiceMethodBaseDAO;

	public void saveOrUpdate(DayStats param) {
		Long id = dubboInvokeBaseDAO.get(CLASSNAME, "getByInvokeDateAndServiceMethod", param);

		if (id != null && id.longValue() > 0) {
			param.setId(id);
			dubboInvokeBaseDAO.update(CLASSNAME, "updateEntity", param);
		} else {
			dubboInvokeBaseDAO.insert(CLASSNAME, "addEntity", param);
		}
	}

	public Map<String, List<StatsSum>> getDatas(DubboDelay param) {
		List<DayStats> list = dubboInvokeBaseDAO.getList(CLASSNAME, "findEntity", param);

		Map<String, List<StatsSum>> map = new HashMap<String, List<StatsSum>>();
		List<StatsSum> slist = getServiceData(list);
		map.put("service", slist);

		slist = getMethodData(list);
		map.put("method", slist);

		return map;
	}

	private List<StatsSum> getServiceData(List<DayStats> list) {
		Map<Long, StatsSum> map = new HashMap<Long, StatsSum>();
		StatsSum ss;

		for (DayStats ds : list) {
			ss = map.get(ds.getServiceId());
			if (ss == null) {
				ss = new StatsSum();
				ss.setName(applicationServiceBaseDAO.getApplicationServiceById(ds.getServiceId()).getName());
				map.put(ds.getServiceId(), ss);
			}
			ss.setNumProvider(ss.getNumProvider() + ds.getSuccessProvider());
			ss.setNumConsumer(ss.getNumConsumer() + ds.getSuccessConsumer());
			ss.setElapsedProvider(ss.getElapsedProvider() + ds.getElapsedProvider());
			ss.setElapsedConsumer(ss.getElapsedConsumer() + ds.getElapsedConsumer());
		}

		List<StatsSum> slist = new ArrayList<StatsSum>(map.values());
		Collections.sort(slist);
		if (slist.size() > 50)
			slist = slist.subList(0, 49);
		return slist;
	}

	private List<StatsSum> getMethodData(List<DayStats> list) {
		Map<Long, StatsSum> map = new HashMap<Long, StatsSum>();
		StatsSum ss;

		for (DayStats ds : list) {
			ss = map.get(ds.getMethodId());
			if (ss == null) {
				ss = new StatsSum();
				ss.setName(applicationServiceMethodBaseDAO.getApplicationServiceMethodById(ds.getMethodId()).getName());
				map.put(ds.getMethodId(), ss);
			}
			ss.setNumProvider(ss.getNumProvider() + ds.getSuccessProvider());
			ss.setNumConsumer(ss.getNumConsumer() + ds.getSuccessConsumer());
			ss.setElapsedProvider(ss.getElapsedProvider() + ds.getElapsedProvider());
			ss.setElapsedConsumer(ss.getElapsedConsumer() + ds.getElapsedConsumer());
		}

		List<StatsSum> slist = new ArrayList<StatsSum>(map.values());
		Collections.sort(slist);

		if (slist.size() > 50)
			slist = slist.subList(0, 49);

		return slist;
	}
}
