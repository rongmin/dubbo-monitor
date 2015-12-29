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
package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class ApplicationServiceMethod implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5468082346847524044L;

	private Long id;
	private Long serviceId;
    private String name ;
    
    private int maxtimeProvider, maxtimeConsumer;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the serviceId
	 */
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}


	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the maxtimeProvider
	 */
	public int getMaxtimeProvider() {
		return maxtimeProvider;
	}

	/**
	 * @param maxtimeProvider the maxtimeProvider to set
	 */
	public void setMaxtimeProvider(int maxtimeProvider) {
		this.maxtimeProvider = maxtimeProvider;
	}

	/**
	 * @return the maxtimeConsumer
	 */
	public int getMaxtimeConsumer() {
		return maxtimeConsumer;
	}

	/**
	 * @param maxtimeConsumer the maxtimeConsumer to set
	 */
	public void setMaxtimeConsumer(int maxtimeConsumer) {
		this.maxtimeConsumer = maxtimeConsumer;
	}

    
    
}
