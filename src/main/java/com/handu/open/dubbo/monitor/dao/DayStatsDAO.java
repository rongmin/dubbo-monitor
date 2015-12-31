package com.handu.open.dubbo.monitor.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceMethodBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.DayStatsBaseDAO;
import com.handu.open.dubbo.monitor.domain.DayStats;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;
import com.handu.open.dubbo.monitor.domain.StatsSum;
import com.handu.open.dubbo.monitor.domain.StatsTable;

@Component
public class DayStatsDAO {

	private static Logger logger = Logger.getLogger(DayStatsDAO.class);

	@Autowired
	private DayStatsBaseDAO dayStatsBaseDAO;

	@Autowired
	ApplicationServiceBaseDAO applicationServiceBaseDAO;

	@Autowired
	ApplicationServiceMethodBaseDAO applicationServiceMethodBaseDAO;

	public void saveOrUpdate(DayStats ds) {
		Long id = dayStatsBaseDAO.getDayStatByInvokeDateAndMethod(ds);

		if (id != null && id.longValue() > 0) {
			ds.setId(id);
			dayStatsBaseDAO.update(ds);
		} else {
			dayStatsBaseDAO.insert(ds);
		}
	}

	public Map<String, List<StatsSum>> getDatas(DubboInvoke dubboInvoke) {
		List<DayStats> list = dayStatsBaseDAO.getList(dubboInvoke);

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
				ss.setName(applicationServiceBaseDAO.getApplicationServiceById(
						ds.getServiceId()).getName());
				map.put(ds.getServiceId(), ss);
			}
			ss.setNumProvider(ss.getNumProvider() + ds.getSuccessProvider());
			ss.setNumConsumer(ss.getNumConsumer() + ds.getSuccessConsumer());
			ss.setElapsedProvider(ss.getElapsedProvider()
					+ ds.getElapsedProvider());
			ss.setElapsedConsumer(ss.getElapsedConsumer()
					+ ds.getElapsedConsumer());
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
				ss.setName(applicationServiceMethodBaseDAO
						.getApplicationServiceMethodById(ds.getMethodId())
						.getName());
				map.put(ds.getMethodId(), ss);
			}
			ss.setNumProvider(ss.getNumProvider() + ds.getSuccessProvider());
			ss.setNumConsumer(ss.getNumConsumer() + ds.getSuccessConsumer());
			ss.setElapsedProvider(ss.getElapsedProvider()
					+ ds.getElapsedProvider());
			ss.setElapsedConsumer(ss.getElapsedConsumer()
					+ ds.getElapsedConsumer());
		}

		List<StatsSum> slist = new ArrayList<StatsSum>(map.values());
		Collections.sort(slist);

		if (slist.size() > 50)
			slist = slist.subList(0, 49);

		return slist;
	}

	public List<StatsTable> getStatsTableList(DubboInvoke dubboInvoke) {

		List<DayStats> list = dayStatsBaseDAO.getList(dubboInvoke);
		List<StatsTable> stList = new ArrayList<StatsTable>();
		Map<Long, StatsTable> map = new HashMap<Long, StatsTable>();

		StatsTable st;
		for (DayStats ds : list) {
			st = map.get(ds.getMethodId());
			if (st == null) {
				st = new StatsTable(ds);
				map.put(ds.getMethodId(), st);				
				st.setServiceName(applicationServiceBaseDAO
						.getApplicationServiceById(ds.getServiceId()).getName());
				st.setMethodName(applicationServiceMethodBaseDAO
						.getApplicationServiceMethodById(ds.getMethodId())
						.getName());
				stList.add(st);
			} else {
				st.addDayStats(ds);
			}
		}
		Collections.sort(stList);

		return stList;
	}
}
