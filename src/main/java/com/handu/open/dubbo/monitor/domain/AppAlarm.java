package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

/**
 * 当注册服务的APP数目低于提供数时，记录流水
 * 
 * @author Jack
 *
 */
public class AppAlarm implements Serializable {
	private static final long serialVersionUID = 3118713652830235205L;

	private Long id;
	private Long appId;
	private Integer providerNum;
	private Integer registerCount;
	private Long invokeTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Integer getProviderNum() {
		return providerNum;
	}

	public void setProviderNum(Integer providerNum) {
		this.providerNum = providerNum;
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Long getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Long invokeTime) {
		this.invokeTime = invokeTime;
	}

}