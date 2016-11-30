package com.practo.urlshortener.models;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsModel {
	private String longUrl;
	private String shortUrl;
	private int totalCount;
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

	public int getTotalCount() {
		return totalCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public List<CountModel> getBrowsersAnytcs() {
		return browsersAnytcs;
	}

	public List<CountModel>  getRefererAnytcs() {
		return refererAnytcs;
	}

	public List<CountModel>  getLocationAnytcs() {
		return locationAnytcs;
	}

	public AnalyticsModel(String longUrl, String shortUrl, int totalCount, Date createdAt, List<CountModel> browsersAnytcs,
			List<CountModel> refererAnytcs, List<CountModel> locationAnytcs) {
		super();
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
		this.totalCount = totalCount;
		this.createdAt = createdAt;
		this.browsersAnytcs = browsersAnytcs;
		this.refererAnytcs = refererAnytcs;
		this.locationAnytcs = locationAnytcs;
	}

}

