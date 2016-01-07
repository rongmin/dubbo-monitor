package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class ZabbixInvoke implements Serializable {
	private static final long serialVersionUID = -5482989839944949144L;

	private long id;
	private String service;
	private String method;
	private String provider;
	private long invokeTime;
	private int success;
	private int failure;
	private int elapsed;
	private int concurrent;
	private int maxElapsed;
	private int maxConcurrent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public long getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(long invokeTime) {
		this.invokeTime = invokeTime;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getElapsed() {
		return elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public int getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}

	public int getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(int maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public int getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}
}