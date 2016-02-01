package com.handu.open.dubbo.monitor.ldap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

/**
 * @author Jack
 *
 */
public class PersonContextMapper extends AbstractContextMapper<Person> {
	@Override
	protected Person doMapFromContext(DirContextOperations ctx) {
		Person p = new Person();
		p.setDn(ctx.getDn().toString());
		p.setUid(ctx.getStringAttribute("uid"));
		p.setGivenName(ctx.getStringAttribute("givenName"));
		p.setDisplayName(ctx.getStringAttribute("displayName"));
		p.setSn(ctx.getStringAttribute("sn"));
		p.setCn(ctx.getStringAttribute("cn"));
		p.setGecos(ctx.getStringAttribute("gecos"));
		p.setMail(ctx.getStringAttribute("mail"));
		p.setHomeDirectory(ctx.getStringAttribute("homeDirectory"));
		p.setUidNumber(ctx.getStringAttribute("uidNumber"));
		p.setGidNumber(ctx.getStringAttribute("gidNumber"));

		String nameInNamespace = ctx.getNameInNamespace();
		if (StringUtils.isNotBlank(nameInNamespace)) {
			p.setPath(nameInNamespace);
		}
		p.setObjectClass(ctx.getStringAttributes("objectClass"));

		return p;
	}
}
