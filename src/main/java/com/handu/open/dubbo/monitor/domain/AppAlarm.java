package com.handu.open.dubbo.monitor.domain;

/**
 * 当自动注册服务的APP数目低于预先定义的数目时，记录流水
 * 
 * @author Jack
 *
 */
public class AppAlarm extends PersistentObject {
	private static final long serialVersionUID = 3118713652830235205L;

	private Long appId;
	private int providerNum;
	private int registerCount;
	private Long invokeTime;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public int getProviderNum() {
		return providerNum;
	}

	public void setProviderNum(int providerNum) {
		this.providerNum = providerNum;
	}

	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	public Long getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Long invokeTime) {
		this.invokeTime = invokeTime;
	}

}