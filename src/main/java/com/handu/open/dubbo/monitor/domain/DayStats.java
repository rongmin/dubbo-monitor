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

import java.util.Date;

public class DayStats extends PersistentObject {
	private static final long serialVersionUID = -5468082346847524044L;

	private Date invokeDate;
	private Long serviceId;
	private Long methodId;
	private long successProvider, failureProvider, elapsedProvider;
	private long successConsumer, failureConsumer, elapsedConsumer;

	public Date getInvokeDate() {
		return invokeDate;
	}

	public void setInvokeDate(Date invokeDate) {
		this.invokeDate = invokeDate;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getMethodId() {
		return methodId;
	}

	public void setMethodId(Long methodId) {
		this.methodId = methodId;
	}

	public long getSuccessProvider() {
		return successProvider;
	}

	public void setSuccessProvider(long successProvider) {
		this.successProvider = successProvider;
	}

	public long getFailureProvider() {
		return failureProvider;
	}

	public void setFailureProvider(long failureProvider) {
		this.failureProvider = failureProvider;
	}

	public long getElapsedProvider() {
		return elapsedProvider;
	}

	public void setElapsedProvider(long elapsedProvider) {
		this.elapsedProvider = elapsedProvider;
	}

	public long getSuccessConsumer() {
		return successConsumer;
	}

	public void setSuccessConsumer(long successConsumer) {
		this.successConsumer = successConsumer;
	}

	public long getFailureConsumer() {
		return failureConsumer;
	}

	public void setFailureConsumer(long failureConsumer) {
		this.failureConsumer = failureConsumer;
	}

	public long getElapsedConsumer() {
		return elapsedConsumer;
	}

	public void setElapsedConsumer(long elapsedConsumer) {
		this.elapsedConsumer = elapsedConsumer;
	}
}
