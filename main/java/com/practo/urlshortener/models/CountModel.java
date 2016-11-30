package com.practo.urlshortener.models;

import java.math.BigInteger;

public class CountModel {
	private String name;
	private BigInteger count;
	
	public CountModel(String name, BigInteger count) {
		this.name = name;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getCount() {
		return count;
	}
	public void setCount(BigInteger count) {
		this.count = count;
	}

}
