package com.ibm.gtmpa.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule implements Serializable {

	private static final long serialVersionUID = -1618471547916468626L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "field_spec", nullable = false)
	private String fieldSpec;

	@NotNull
	@Column(name = "rule", nullable = false)
	private String rule;

	@Column(name = "backstate", nullable = true)
	private String backState;

	@Column(name = "forwardstate", nullable = true)
	private String forwardState;

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

	public String getForwardState() {
		return forwardState;
	}

	public void setForwardState(String forwardState) {
		this.forwardState = forwardState;
	}

	public String getBackState() {
		return backState;
	}

	public void setBackState(String backState) {
		this.backState = backState;
	}

	public String getFieldSpec() {
		return fieldSpec;
	}

	public void setFieldSpec(String fieldSpec) {
		this.fieldSpec = fieldSpec;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Rule rule = (Rule) o;

		if (!Objects.equals(id, rule.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);

	}
}
