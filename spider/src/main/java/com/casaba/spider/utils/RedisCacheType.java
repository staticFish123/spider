package com.casaba.spider.utils;

public enum RedisCacheType{
	
	USER_URL("userUrl");
	
	private String name;
	
	private RedisCacheType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
