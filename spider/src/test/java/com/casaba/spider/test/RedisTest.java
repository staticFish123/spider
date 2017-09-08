package com.casaba.spider.test;

import java.io.Serializable;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.redis.service.RedisDataSource;
import com.casaba.spider.utils.RedisCacheType;

public class RedisTest extends BaseTest{
	
	@Autowired
	private RedisDataSource redisDataSource;
	
	@Test
	public void addDataToSetCollection() {
		redisDataSource.setValueinSetCollection(RedisCacheType.USER_URL, "lin");
		redisDataSource.setValueinSetCollection(RedisCacheType.USER_URL, "Jack");
		redisDataSource.setValueinSetCollection(RedisCacheType.USER_URL, "Tz");
		redisDataSource.setValueinSetCollection(RedisCacheType.USER_URL, "Tz");
		redisDataSource.setValueinSetCollection(RedisCacheType.USER_URL, "lin");
		Set<Serializable> set = redisDataSource.getValueFromSetCollection(RedisCacheType.USER_URL);
		assert(set.size() == 3);
	}
}
