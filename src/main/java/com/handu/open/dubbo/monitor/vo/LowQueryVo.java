package com.handu.open.dubbo.monitor.vo;

public class LowQueryVo {
	private Long serviceId;
	private String serviceName;
	private String invokeDateFrom;
	private String invokeDateTo;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getInvokeDateFrom() {
		return invokeDateFrom;
	}

	public void setInvokeDateFrom(String invokeDateFrom) {
		this.invokeDateFrom = invokeDateFrom;
	}

	public String getInvokeDateTo() {
		return invokeDateTo;
	}

	public void setInvokeDateTo(String invokeDateTo) {
		this.invokeDateTo = invokeDateTo;
	}

}
