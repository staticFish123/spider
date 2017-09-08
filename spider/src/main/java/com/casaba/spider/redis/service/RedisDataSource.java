package com.casaba.spider.redis.service;

import java.io.Serializable;
import java.util.Set;

import com.casaba.spider.utils.RedisCacheType;

public interface RedisDataSource {
	
	void setValueinSetCollection(RedisCacheType cacheType, String value);
	
	Set<Serializable> getValueFromSetCollection(RedisCacheType cacheType);
}
