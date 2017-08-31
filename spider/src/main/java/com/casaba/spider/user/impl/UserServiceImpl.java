package com.casaba.spider.user.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casaba.spider.factory.HttpClientFactory;
import com.casaba.spider.share.Collection;
import com.casaba.spider.threads.UserUrlHandler;
import com.casaba.spider.user.UserService;
import com.casaba.spider.utils.ConfigProperty;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static ExecutorService pool;
	
	/* (non-Javadoc)
	 * @see com.casaba.spider.user.UserService#getAllUserURL()
	 */
	@Override
	public void getAllUserURL() throws IOException, InterruptedException {
		
		int userUrlThreadNum = Integer.parseInt(ConfigProperty.getInstance().getProperty("userUrlThreadNum"));
		pool = Executors.newFixedThreadPool(userUrlThreadNum);
		int maxTotal = Integer.parseInt(ConfigProperty.getInstance().getProperty("maxTotal"));
		int defaultMaxPerRoute = Integer.parseInt(ConfigProperty.getInstance().getProperty("defaultMaxPerRoute"));
		CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(maxTotal, defaultMaxPerRoute);
		
		logger.info("SubTopicID Size: " + Collection.subTopicIDQueue.size());
		
		int len = Collection.subTopicIDQueue.size();
		for(int i=0; i<len;i++) {
			try {
				HttpPost httppost = new HttpPost(
						"https://www.zhihu.com/topic/" + Collection.subTopicIDQueue.take() + "/followers");
				System.out.println(httppost.getURI());
				 pool.execute(new UserUrlHandler(httpClient,httppost));
				 httppost.releaseConnection();
			} catch (InterruptedException e) {
				logger.error("InterruptedException", e);
			}
		}
		
		pool.shutdown();
		while(true) {
			
			logger.info("剩余Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
			
			if(pool.isTerminated()) {
				httpClient.close();
				logger.info("所有子线程都已经结束了,Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
			}
			
			Thread.sleep(1000);
		}
		
		
	}

}
