package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;
import java.util.Date;

public class DubboDelay implements Serializable {
	private static final long serialVersionUID = 2574275812067932169L;

	private Long id;
	private Date invokeDate;
	private Long serviceId;
	private Long methodId;
	private String consumer;
	private String provider;
	private String type;
	private int success;
	private int failure;
	private int elapsed;
	private int concurrent;
	private int maxElapsed;
	private int maxConcurrent;
	private long invokeTime;
	// ====================查询辅助参数===================
	/**
	 * 统计时间粒度(毫秒)
	 */
	private long timeParticle = 60000;
	private Date invokeDateFrom;
	private Date invokeDateTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public long getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(long invokeTime) {
		this.invokeTime = invokeTime;
	}

	public long getTimeParticle() {
		return timeParticle;
	}

	public void setTimeParticle(long timeParticle) {
		this.timeParticle = timeParticle;
	}

	public Date getInvokeDateFrom() {
		return invokeDateFrom;
	}

	public void setInvokeDateFrom(Date invokeDateFrom) {
		this.invokeDateFrom = invokeDateFrom;
	}

	public Date getInvokeDateTo() {
		return invokeDateTo;
	}

	public void setInvokeDateTo(Date invokeDateTo) {
		this.invokeDateTo = invokeDateTo;
	}

}
