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

public class ApplicationServiceMethod extends PersistentObject {
	private static final long serialVersionUID = -5468082346847524044L;

	private Long serviceId;
	private String name;
	private String serviceName;
	private int maxtimeProvider, maxtimeConsumer;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getMaxtimeProvider() {
		return maxtimeProvider;
	}

	public void setMaxtimeProvider(int maxtimeProvider) {
		this.maxtimeProvider = maxtimeProvider;
	}

	public int getMaxtimeConsumer() {
		return maxtimeConsumer;
	}

	public void setMaxtimeConsumer(int maxtimeConsumer) {
		this.maxtimeConsumer = maxtimeConsumer;
	}

}
