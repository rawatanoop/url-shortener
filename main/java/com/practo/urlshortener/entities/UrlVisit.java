/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.practo.urlshortener.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author anoop
 */
@Entity
@Table(name = "url_visit")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "UrlVisit.findAll", query = "SELECT u FROM UrlVisit u"),
		@NamedQuery(name = "UrlVisit.findById", query = "SELECT u FROM UrlVisit u WHERE u.id = :id"),
		@NamedQuery(name = "UrlVisit.findByClickedAt", query = "SELECT u FROM UrlVisit u WHERE u.clickedAt = :clickedAt") })
public class UrlVisit implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "Clicked_At")
	@Temporal(TemporalType.TIMESTAMP)
	private Date clickedAt;
	@JoinColumn(name = "URL", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Urls url;
	@JoinColumn(name = "Browser", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Browser browser;
	@JoinColumn(name = "Location", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Country location;
	@JoinColumn(name = "Referer", referencedColumnName = "ID")
	@ManyToOne(optional = false)
	private Referer referer;

	public UrlVisit() {
	}

	public UrlVisit(Integer id) {
		this.id = id;
	}

	public UrlVisit(Integer id, Date clickedAt) {
		this.id = id;
		this.clickedAt = clickedAt;
	}

	public UrlVisit(Urls url, Browser browser, Referer referer, Country country) {
		this.url = url;
		this.browser = browser;
		this.referer = referer;
		this.location = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getClickedAt() {
		return clickedAt;
	}

	public void setClickedAt(Date clickedAt) {
		this.clickedAt = clickedAt;
	}

	public Urls getUrl() {
		return url;
	}

	public void setUrl(Urls url) {
		this.url = url;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	public Country getLocation() {
		return location;
	}

	public void setLocation(Country location) {
		this.location = location;
	}

	public Referer getReferer() {
		return referer;
	}

	public void setReferer(Referer referer) {
		this.referer = referer;
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
		if (!(object instanceof UrlVisit)) {
			return false;
		}
		UrlVisit other = (UrlVisit) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.UrlVisit[ id=" + id + " ]";
	}

}
