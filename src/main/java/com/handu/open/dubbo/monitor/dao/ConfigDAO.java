package com.handu.open.dubbo.monitor.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceBaseDAO;
import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceMethodBaseDAO;
import com.handu.open.dubbo.monitor.domain.ApplicationService;
import com.handu.open.dubbo.monitor.domain.ApplicationServiceMethod;

@Component
public class ConfigDAO {

	private static Logger logger = Logger.getLogger(ConfigDAO.class);  
	
	@Autowired
	private ApplicationServiceBaseDAO applicationServiceBaseDAO;

	@Autowired
	private ApplicationServiceMethodBaseDAO applicationBaseServiceMethodDAO;

	
	public long getServiceId(  String str) {

		Long id = applicationServiceBaseDAO.getIdByName(str);

		if (id == null) {
			ApplicationService as = new ApplicationService();			
			as.setName(str);
			applicationServiceBaseDAO.insert(as);
			id = as.getId();
			if(id==null){
				logger.info("  ok "  + id  );
			}else{
				logger.info("  isert id:=  "  + id  );
			}
		}
		return id;
	}

	
	public long getMethodId(long serviceId, String str) {

		Long id = applicationBaseServiceMethodDAO.getIdByName(serviceId, str);

		if (id == null) {
			ApplicationServiceMethod asm = new ApplicationServiceMethod();
			asm.setServiceId(serviceId);
			asm.setName(str);
			asm.setMaxtimeProvider(120);
			asm.setMaxtimeConsumer(150);
			applicationBaseServiceMethodDAO.insert(asm);
			id = asm.getId();
		}
		return id;
	}

	/*private boolean notExist(String str, List<Application> list) {
		for (Application app : list) {
			if (app.getName().equals(str))
				return true;
		}
		return false;
	}*/

}
