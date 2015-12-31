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


public class StatsTable implements Comparable<StatsTable>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5468082346847524044L;

	private long methodId;
	private String appName, serviceName, methodName;
	private int successProvider, failureProvider, elapsedProvider;
	private int successConsumer, failureConsumer, elapsedConsumer;

	public StatsTable(DayStats ds) {
		this.successProvider = ds.getSuccessProvider();
		this.successConsumer = ds.getSuccessConsumer();
		this.failureProvider = ds.getFailureProvider();
		this.failureConsumer = ds.getFailureConsumer();
		this.elapsedProvider = ds.getElapsedProvider();
		this.elapsedConsumer =  ds.getElapsedConsumer();
		this.methodId = ds.getMethodId();
	}

	/**
	 * @return the methodId
	 */
	public long getMethodId() {
		return methodId;
	}

	/**
	 * @param methodId
	 *            the methodId to set
	 */
	public void setMethodId(long methodId) {
		this.methodId = methodId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 *            the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the successProvider
	 */
	public int getSuccessProvider() {
		return successProvider;
	}

	/**
	 * @param successProvider
	 *            the successProvider to set
	 */
	public void setSuccessProvider(int successProvider) {
		this.successProvider = successProvider;
	}

	/**
	 * @return the failureProvider
	 */
	public int getFailureProvider() {
		return failureProvider;
	}

	/**
	 * @param failureProvider
	 *            the failureProvider to set
	 */
	public void setFailureProvider(int failureProvider) {
		this.failureProvider = failureProvider;
	}

	/**
	 * @return the elapsedProvider
	 */
	public int getElapsedProvider() {
		return elapsedProvider;
	}

	/**
	 * @param elapsedProvider
	 *            the elapsedProvider to set
	 */
	public void setElapsedProvider(int elapsedProvider) {
		this.elapsedProvider = elapsedProvider;
	}

	/**
	 * @return the successConsumer
	 */
	public int getSuccessConsumer() {
		return successConsumer;
	}

	/**
	 * @param successConsumer
	 *            the successConsumer to set
	 */
	public void setSuccessConsumer(int successConsumer) {
		this.successConsumer = successConsumer;
	}

	/**
	 * @return the failureConsumer
	 */
	public int getFailureConsumer() {
		return failureConsumer;
	}

	/**
	 * @param failureConsumer
	 *            the failureConsumer to set
	 */
	public void setFailureConsumer(int failureConsumer) {
		this.failureConsumer = failureConsumer;
	}

	/**
	 * @return the elapsedConsumer
	 */
	public int getElapsedConsumer() {
		return elapsedConsumer;
	}

	/**
	 * @param elapsedConsumer
	 *            the elapsedConsumer to set
	 */
	public void setElapsedConsumer(int elapsedConsumer) {
		this.elapsedConsumer = elapsedConsumer;
	}

	public void addDayStats(DayStats ds) {
		this.successProvider += ds.getSuccessProvider();
		this.successConsumer += ds.getSuccessConsumer();
		this.failureProvider += ds.getFailureProvider();
		this.failureConsumer += ds.getFailureConsumer();
		this.elapsedProvider += ds.getElapsedProvider();
		this.elapsedConsumer +=  ds.getElapsedConsumer();		
	}

	public long getAverageElapsedProvider(){
		long t = successProvider + failureProvider;
		if(t>0)
			return elapsedProvider*1000/t;
		return 0;
	}

	public long getAverageElapsedConsumer(){
		long t = successConsumer + failureConsumer;
		if(t>0)
			return elapsedConsumer*1000/t;
		return 0;
	}
	
	
	public int compareTo(StatsTable o) {
		int ok = this.appName.compareTo(o.getAppName());
		if(ok==0){
			ok = this.serviceName.compareTo(o.getServiceName());
			if(ok==0){
				ok = this.methodName.compareTo(o.getMethodName());
			}
		}
		if(ok==0){
			if(this.successProvider > o.getSuccessProvider())				
				return 1;
			if(this.successProvider < o.getSuccessProvider())
				return -1;
			return 0;
		}
		return ok;
	}

}
