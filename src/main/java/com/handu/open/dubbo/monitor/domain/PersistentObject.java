package com.handu.open.dubbo.monitor.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PersistentObject implements Serializable {
	private static final long serialVersionUID = -5240423565384546267L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && PersistentObject.class.isAssignableFrom(obj.getClass())) {
			PersistentObject p = (PersistentObject) obj;
			result = new EqualsBuilder().append(id, p.getId()).isEquals();
		}
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
