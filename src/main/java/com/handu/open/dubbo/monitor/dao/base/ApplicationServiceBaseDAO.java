/**
 * Copyright 2006-2015 handu.com

 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handu.open.dubbo.monitor.dao.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.handu.open.dubbo.monitor.domain.ApplicationService;

@Repository
public class ApplicationServiceBaseDAO extends SqlSessionDaoSupport {

	private List<ApplicationService> list;

	private Map<String,Long>  nameIdMap  = new HashMap<String,Long>();
	
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public void insert( ApplicationService as) {
        getSqlSession().insert("monitor.addApplicationService", as);
        list = null;
        nameIdMap.clear();
    }

    public List<ApplicationService> getList() {
    	initData();
        return list;
    }
    
    public Long getIdByName(long appId,String str) {
    	initData();
        return nameIdMap.get(appId+"."+str);
    }

    private void initData(){
    	if(list==null){
    		list = getSqlSession().selectList("monitor.getAllApplicationService");
    		for(ApplicationService as: list){
    			nameIdMap.put(as.getAppId()+"."+as.getName(), as.getId());
    		}
    	}
    }
}
