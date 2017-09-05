package com.casaba.spider.user.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	private int queueCount; //当前排队线程数
	private int activeCount; //当前活动线程数
	private long completedTask; //执行完成线程数
	private long totalTask; //总线程数
	
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
//				logger.info("剩余Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
				System.out.println(httppost.getURI());
				 pool.execute(new UserUrlHandler(httpClient,httppost));
				 httppost.releaseConnection();
			} catch (InterruptedException e) {
				logger.error("InterruptedException", e);
			}
		}
		
		pool.shutdown();
		
		while(true) {
			
//			logger.info("剩余Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
			queueCount = ((ThreadPoolExecutor)pool).getQueue().size();
			activeCount = ((ThreadPoolExecutor)pool).getActiveCount();
			completedTask = ((ThreadPoolExecutor)pool).getCompletedTaskCount();
			totalTask = ((ThreadPoolExecutor)pool).getTaskCount();
			
			logger.info("当前排队Sub Topic ID个数为: " + queueCount);
			logger.info("当前运行Sub Topic ID个数为: " + activeCount);
			logger.info("当前完成Sub Topic ID个数为: " + completedTask);
			logger.info("总共Sub Topic ID个数为： " + totalTask);
			
			if(pool.isTerminated()) {
				httpClient.close();
				logger.info("所有子线程都已经结束了,Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
			}
			
			Thread.sleep(1000);
		}
		
		
	}

}
