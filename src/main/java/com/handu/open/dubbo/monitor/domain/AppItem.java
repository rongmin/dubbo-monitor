package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class AppItem implements Serializable {
	private static final long serialVersionUID = 7154467856109028794L;

	private Long id;
	private String name;
	private Integer providerNum;
	private String owner;
	private String provider;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProviderNum() {
		return providerNum;
	}

	public void setProviderNum(Integer providerNum) {
		this.providerNum = providerNum;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}