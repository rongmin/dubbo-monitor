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
import java.util.Date;

public class DayStats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5468082346847524044L;

	private long id;

	private Date invokeDate;
	private long serviceId, methodId;
	private long successProvider, failureProvider, elapsedProvider;
	private long successConsumer, failureConsumer, elapsedConsumer;


	


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}





	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}





	/**
	 * @return the invokeDate
	 */
	public Date getInvokeDate() {
		return invokeDate;
	}





	/**
	 * @param invokeDate the invokeDate to set
	 */
	public void setInvokeDate(Date invokeDate) {
		this.invokeDate = invokeDate;
	}





	/**
	 * @return the serviceId
	 */
	public long getServiceId() {
		return serviceId;
	}





	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}





	/**
	 * @return the methodId
	 */
	public long getMethodId() {
		return methodId;
	}





	/**
	 * @param methodId the methodId to set
	 */
	public void setMethodId(long methodId) {
		this.methodId = methodId;
	}





	/**
	 * @return the successProvider
	 */
	public long getSuccessProvider() {
		return successProvider;
	}





	/**
	 * @param successProvider the successProvider to set
	 */
	public void setSuccessProvider(long successProvider) {
		this.successProvider = successProvider;
	}





	/**
	 * @return the failureProvider
	 */
	public long getFailureProvider() {
		return failureProvider;
	}





	/**
	 * @param failureProvider the failureProvider to set
	 */
	public void setFailureProvider(long failureProvider) {
		this.failureProvider = failureProvider;
	}





	/**
	 * @return the elapsedProvider
	 */
	public long getElapsedProvider() {
		return elapsedProvider;
	}





	/**
	 * @param elapsedProvider the elapsedProvider to set
	 */
	public void setElapsedProvider(long elapsedProvider) {
		this.elapsedProvider = elapsedProvider;
	}





	/**
	 * @return the successConsumer
	 */
	public long getSuccessConsumer() {
		return successConsumer;
	}





	/**
	 * @param successConsumer the successConsumer to set
	 */
	public void setSuccessConsumer(long successConsumer) {
		this.successConsumer = successConsumer;
	}





	/**
	 * @return the failureConsumer
	 */
	public long getFailureConsumer() {
		return failureConsumer;
	}





	/**
	 * @param failureConsumer the failureConsumer to set
	 */
	public void setFailureConsumer(long failureConsumer) {
		this.failureConsumer = failureConsumer;
	}





	/**
	 * @return the elapsedConsumer
	 */
	public long getElapsedConsumer() {
		return elapsedConsumer;
	}





	/**
	 * @param elapsedConsumer the elapsedConsumer to set
	 */
	public void setElapsedConsumer(long elapsedConsumer) {
		this.elapsedConsumer = elapsedConsumer;
	}





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DayStats [id=" + id + ", invokeDate=" + invokeDate 
				+  ", serviceId=" + serviceId + ", methodId=" + methodId
				+ ", successProvider=" + successProvider + ", failureProvider="
				+ failureProvider + ", elapsedProvider=" + elapsedProvider
				+ ", successConsumer=" + successConsumer + ", failureConsumer="
				+ failureConsumer + ", elapsedConsumer=" + elapsedConsumer
				+  "]";
	}
}
