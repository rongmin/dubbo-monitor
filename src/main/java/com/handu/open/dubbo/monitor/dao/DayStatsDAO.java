package com.handu.open.dubbo.monitor.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.handu.open.dubbo.monitor.dao.base.DayStatsBaseDAO;
import com.handu.open.dubbo.monitor.domain.DayStats;

@Component
public class DayStatsDAO {

	
	 private static Logger logger = Logger.getLogger(DayStatsDAO.class);  
	
	@Autowired
	private DayStatsBaseDAO dayStatsBaseDAO;
	
	public void saveOrUpdate(DayStats ds){
		if(ds.getId() > 0){
			dayStatsBaseDAO.update(ds);
		}else{
			dayStatsBaseDAO.insert(ds);
			logger.info(" dayStatsBaseDAO.insert(ds): " + ds );
			System.out.println(" dayStatsBaseDAO.insert(ds): " + ds );
		}				
	}
}
