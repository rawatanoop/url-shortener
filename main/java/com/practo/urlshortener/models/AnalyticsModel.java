package com.practo.urlshortener.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class AnalyticsModel {
	private String longUrl;
	private String shortUrl;
	private BigInteger totalCount;
	private Date createdAt;
	private List<CountModel> browsersAnytcs;
	private List<CountModel> refererAnytcs;
	private List<CountModel> locationAnytcs;

	public String getLongUrl() {
		return longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public List<CountModel> getBrowsersAnytcs() {
		return browsersAnytcs;
	}

	public List<CountModel> getRefererAnytcs() {
		return refererAnytcs;
	}

	public List<CountModel> getLocationAnytcs() {
		return locationAnytcs;
	}

	public AnalyticsModel(String longUrl, String shortUrl, BigInteger totalCount, Date createdAt) {
		super();
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
		this.totalCount = totalCount;
		this.createdAt = createdAt;

	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	public void setBrowsersAnytcs(List<CountModel> browsersAnytcs) {
		this.browsersAnytcs = browsersAnytcs;
	}

	public void setRefererAnytcs(List<CountModel> refererAnytcs) {
		this.refererAnytcs = refererAnytcs;
	}

	public void setLocationAnytcs(List<CountModel> locationAnytcs) {
		this.locationAnytcs = locationAnytcs;
	}

}
