package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class DubboMethod implements Serializable {
	private static final long serialVersionUID = 6128218854035018873L;

	private String name;
	private String service;
	private String application;
	private String organization;
	private String owner;
	private int providerCount;
	private int consumerCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(int providerCount) {
		this.providerCount = providerCount;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}
}
