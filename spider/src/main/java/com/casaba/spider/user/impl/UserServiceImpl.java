package com.casaba.spider.user.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casaba.spider.factory.HttpClientFactory;
import com.casaba.spider.share.Collection;
import com.casaba.spider.user.UserService;
import com.casaba.spider.utils.ConfigProperty;

public class UserServiceImpl implements UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static ExecutorService pool;
	
	@Override
	public void getAllUserURL() {
		
		int userUrlThreadNum = Integer.parseInt(ConfigProperty.getInstance().getProperty("userUrlThreadNum"));
		pool = Executors.newFixedThreadPool(userUrlThreadNum);
		int maxTotal = Integer.parseInt(ConfigProperty.getInstance().getProperty("maxTotal"));
		int defaultMaxPerRoute = Integer.parseInt(ConfigProperty.getInstance().getProperty("defaultMaxPerRoute"));
		CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(maxTotal, defaultMaxPerRoute);
		
		logger.info("SubTopicID Size: " + Collection.subTopicIDQueue.size());
		
		
		
		
	}

}
