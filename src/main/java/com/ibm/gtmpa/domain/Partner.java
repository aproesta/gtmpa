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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partner implements Serializable {

	private static final long serialVersionUID = 4007813659994897108L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 3, max = 20)
	@Column(name = "name", length = 20, nullable = false)
	private String name;

	@NotNull
	@Size(min = 5, max = 40)
	@Column(name = "contactname", length = 40, nullable = false)
	private String contactName;

	@NotNull
	@Size(min = 5, max = 40)
	@Column(name = "contactemail", length = 40, nullable = false)
	private String contactEmail;

	@NotNull
	@Size(min = 4, max = 10)
	@Column(name = "pin", length = 10, nullable = false)
	private String pin;

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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Partner partner = (Partner) o;

		if (!Objects.equals(id, partner.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Partner{" + "id=" + id + ", name='" + name + "'" + ", contactName='" + contactName + "'"
				+ ", contactEmail='" + contactEmail + "'" + ", pin='" + pin + "'" + '}';
	}
}
