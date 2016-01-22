package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

public class AppItem implements Serializable {
	private static final long serialVersionUID = 7154467856109028794L;

	private Long id;
	private String name;
	private Integer providerNum;
	private String owner;
	private String provider;
	// 共页面显示使用,当前容器中注册的提供者及个数
	private Integer registerNum;
	private String register;
	private boolean diff = false;
	private boolean numDiff = false;

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

	public Integer getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public boolean isDiff() {
		return diff;
	}

	public void setDiff(boolean diff) {
		this.diff = diff;
	}

	public boolean isNumDiff() {
		return numDiff;
	}

	public void setNumDiff(boolean numDiff) {
		this.numDiff = numDiff;
	}

}