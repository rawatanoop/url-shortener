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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "urls")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Urls.findAll", query = "SELECT u FROM Urls u"),
		@NamedQuery(name = "Urls.findById", query = "SELECT u FROM Urls u WHERE u.id = :id"),
		@NamedQuery(name = "Urls.findByUrl", query = "SELECT u FROM Urls u WHERE u.url = :url"),
		@NamedQuery(name = "Urls.findByCreatedAt", query = "SELECT u FROM Urls u WHERE u.createdAt = :createdAt") })
public class Urls implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Long id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 700)
	@Column(name = "URL")
	private String url;
	@Basic(optional = false)

	@Column(name = "Created_At")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "url")
	private Collection<UrlVisit> urlVisitCollection;
	@JoinColumn(name = "User_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Users userID;

	public Urls() {
	}

	public Urls(Long id) {
		this.id = id;
	}

	public Urls( String url,Users user) {
		this.userID = user;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@XmlTransient
	public Collection<UrlVisit> getUrlVisitCollection() {
		return urlVisitCollection;
	}

	public void setUrlVisitCollection(Collection<UrlVisit> urlVisitCollection) {
		this.urlVisitCollection = urlVisitCollection;
	}

	public Users getUserID() {
		return userID;
	}

	public void setUserID(Users userID) {
		this.userID = userID;
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
		if (!(object instanceof Urls)) {
			return false;
		}
		Urls other = (Urls) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.Urls[ id=" + id + " ]";
	}

}
