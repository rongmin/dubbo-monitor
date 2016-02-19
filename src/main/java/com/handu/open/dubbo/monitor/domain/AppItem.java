package com.handu.open.dubbo.monitor.domain;

/**
 * Application
 * 
 * @author Jack
 *
 */
public class AppItem extends PersistentObject {
	private static final long serialVersionUID = 7154467856109028794L;

	private String name;
	private String owner;// 提供者
	// 预先定义的提供者及数目
	private int providerNum;
	private String provider;

	/*** 供页面显示使用 ***/
	// 当前容器中自动注册的提供者及数目
	private int registerNum;
	private String register;
	private boolean diff = false;// 提供者是否一致
	private boolean numDiff = false;// 提供数是否一致

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getProviderNum() {
		return providerNum;
	}

	public void setProviderNum(int providerNum) {
		this.providerNum = providerNum;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(int registerNum) {
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