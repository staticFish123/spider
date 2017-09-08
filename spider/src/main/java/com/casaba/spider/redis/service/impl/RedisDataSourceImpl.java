package com.casaba.spider.redis.service.impl;

import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.casaba.spider.redis.service.RedisDataSource;
import com.casaba.spider.utils.RedisCacheType;

@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(RedisDataSourceImpl.class);
	
	@Autowired
	private RedisTemplate<Serializable, Serializable> resdisTemplate;
	
	private static SetOperations<Serializable, Serializable> setOperations;

	@Override
	public void setValueinSetCollection(RedisCacheType cacheType, String value) {
		setOperations = resdisTemplate.opsForSet();
		setOperations.add(cacheType.getName(), value);
	}
	
	@Override
	public Set<Serializable> getValueFromSetCollection(RedisCacheType cacheType) {
		return setOperations.members(cacheType.getName());
	}

}
