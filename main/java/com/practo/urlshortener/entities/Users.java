/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.practo.urlshortener.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author anoop
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
		@NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
		@NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name"),
		@NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
		@NamedQuery(name = "Users.findByRegisteredAt", query = "SELECT u FROM Users u WHERE u.registeredAt = :registeredAt"),
		@NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email") })
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "Name")
	private String name;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "Password")
	private String password;
	@Basic(optional = false)

	@Column(name = "Registered_At")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registeredAt;
	// @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	// message="Invalid email")//if the field contains email address consider
	// using this annotation to enforce field validation
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "Email")
	private String email;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
	private Collection<Urls> urlsCollection;

	public Users() {
	}

	public Users(Integer id) {
		this.id = id;
	}

	public Users(Integer id, String name, String password, Date registeredAt, String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.registeredAt = registeredAt;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(Date registeredAt) {
		this.registeredAt = registeredAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlTransient
	public Collection<Urls> getUrlsCollection() {
		return urlsCollection;
	}

	public void setUrlsCollection(Collection<Urls> urlsCollection) {
		this.urlsCollection = urlsCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Users)) {
			return false;
		}
		Users other = (Users) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Users[ id=" + id + " ]";
	}

}
