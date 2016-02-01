package com.handu.open.dubbo.monitor.ldap;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.Collections;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapName;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.AuthenticatedLdapEntryContextMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapEntryIdentification;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;

public class AuthenticateService implements BaseLdapNameAware {
	private LdapName basePath;
	private LdapTemplate ldapTemplate;
	private String ou;
	private String objectclass;

	public AuthenticateService() {
	}

	public Person lookup(String uid) {
		Name dn = buildDn(uid);
		return lookup(dn);
	}

	public Person lookup(Name dn) {
		return ldapTemplate.lookup(dn, getContextMapper());
	}

	public Person search(String uid) {
		LdapQuery query = buildUidQuery(uid);
		List<Person> list = search(query);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	private List<Person> search(LdapQuery query) {
		List<Person> list = ldapTemplate.search(query, getContextMapper());
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public List<String> findAllUids() {
		return ldapTemplate.search(query().where("objectclass").is(getObjectclass()), new AttributesMapper<String>() {

			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return attrs.get("uid").get().toString();
			}
		});
	}

	public List<Person> findAll() {
		EqualsFilter filter = new EqualsFilter("objectClass", getObjectclass());
		return ldapTemplate.search(LdapUtils.emptyLdapName(), filter.encode(), getContextMapper());
	}

	public boolean authenticate(String uid, String password) {
		AuthenticatedLdapEntryContextMapper<DirContextOperations> mapper = new AuthenticatedLdapEntryContextMapper<DirContextOperations>() {

			public DirContextOperations mapWithContext(DirContext ctx,
					LdapEntryIdentification ldapEntryIdentification) {
				try {
					return (DirContextOperations) ctx.lookup(ldapEntryIdentification.getRelativeName());
				} catch (NamingException e) {
					throw new RuntimeException("Failed to lookup " + ldapEntryIdentification.getRelativeName());
				}
			}
		};
		try {
			ldapTemplate.authenticate(buildUidQuery(uid), password, mapper);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private LdapQuery buildUidQuery(String uid) {
		LdapQuery qStr = query().where("objectclass").is(getObjectclass()).and("uid").whitespaceWildcardsLike(uid);
		return qStr;
	}

	private Name buildDn(String uid) {
		if (StringUtils.isBlank(getOu()) || StringUtils.isBlank(uid)) {
			throw new RuntimeException("ou or uid can't be empty.");
		}
		return LdapNameBuilder.newInstance().add("ou", getOu()).add("uid", uid).build();
	}

	public LdapName getFullPersonDn(String uid) {
		if (StringUtils.isBlank(getOu()) || StringUtils.isBlank(uid)) {
			throw new RuntimeException("ou or uid can't be empty.");
		}
		return LdapNameBuilder.newInstance(basePath).add("ou", getOu()).add("uid", uid).build();
	}

	private ContextMapper<Person> getContextMapper() {
		return new PersonContextMapper();
	}

	public void setBaseLdapPath(LdapName basePath) {
		this.basePath = basePath;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getObjectclass() {
		return objectclass;
	}

	public void setObjectclass(String objectclass) {
		this.objectclass = objectclass;
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationLdap.xml");
		AuthenticateService authenticateService = ctx.getBean("authenticateService", AuthenticateService.class);
		Person person = authenticateService.lookup("wangjie");
		System.out.println("lookup: " + person);
		person = authenticateService.search("wangjie");
		System.out.println("search: " + person);

		List<String> uids = authenticateService.findAllUids();
		for (String str : uids) {
			System.out.println("findAllUids: " + str);
		}
		System.out.println("size: " + uids.size());

		List<Person> persons = authenticateService.findAll();
		for (Person p : persons) {
			System.out.println("findAll: " + p);
		}
		System.out.println(authenticateService.authenticate("wangjie", "IveneLlAFrEt"));
	}

}
