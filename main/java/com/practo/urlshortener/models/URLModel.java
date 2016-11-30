package com.practo.urlshortener.models;

public class URLModel {
	private String shortURL;
	private String longURL;

	public URLModel() {
	}

	public URLModel(String longURL, String shortURL) {
		this.longURL = longURL;
		this.shortURL = shortURL;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

	public String getLongURL() {
		return longURL;
	}

	public void setLongURL(String longURL) {
		this.longURL = longURL;
	}

}
