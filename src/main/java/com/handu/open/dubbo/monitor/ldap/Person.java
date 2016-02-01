package com.handu.open.dubbo.monitor.ldap;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * 
 * objectClass[0]=inetOrgPerson, objectClass[1]=posixAccount,
 * objectClass[2]=shadowAccount
 * 
 * @author Jack
 *
 */
public class Person {
	private String dn;
	private String uid;
	private String ou;
	private String givenName;
	private String displayName;
	private String sn;
	private String cn;
	private String gecos;
	private String mail;
	private String homeDirectory;
	private String uidNumber;
	private String gidNumber;
	private String path;
	private String[] objectClass;

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getGecos() {
		return gecos;
	}

	public void setGecos(String gecos) {
		this.gecos = gecos;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getHomeDirectory() {
		return homeDirectory;
	}

	public void setHomeDirectory(String homeDirectory) {
		this.homeDirectory = homeDirectory;
	}

	public String getUidNumber() {
		return uidNumber;
	}

	public void setUidNumber(String uidNumber) {
		this.uidNumber = uidNumber;
	}

	public String getGidNumber() {
		return gidNumber;
	}

	public void setGidNumber(String gidNumber) {
		this.gidNumber = gidNumber;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String[] objectClass) {
		this.objectClass = objectClass;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
