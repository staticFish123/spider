package com.casaba.spider.user.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.factory.HttpClientFactory;
import com.casaba.spider.model.UserUrl;
import com.casaba.spider.redis.service.RedisDataSource;
import com.casaba.spider.share.Collection;
import com.casaba.spider.threads.UserInfoHandler;
import com.casaba.spider.threads.UserUrlHandler;
import com.casaba.spider.user.UserService;
import com.casaba.spider.utils.ConfigProperty;
import com.casaba.spider.utils.RedisCacheType;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("init.xml");
	private static IUserUrl iUserUrl = ctx.getBean(IUserUrl.class);
	private static RedisDataSource redisDataSource = ctx.getBean(RedisDataSource.class);
	
	private static ExecutorService pool;
	private static ExecutorService userPool;
	
	private int queueCount; //当前排队线程数
	private int activeCount; //当前活动线程数
	private long completedTask; //执行完成线程数
	private long totalTask; //总线程数
	
	private final static UserUrl UserUrl = new UserUrl();
	
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
			
			showPoolStatus(pool, "Sub Topic ID");
			
			if(pool.isTerminated()) {
				httpClient.close();
				logger.info("所有子线程都已经结束了,Sub Topic ID个数为" + Collection.subTopicIDQueue.size());
				logger.info("开始将数据存入数据库");
				Set<Serializable> set = redisDataSource.getValueFromSetCollection(RedisCacheType.USER_URL);
				Iterator<Serializable> iterator = set.iterator();
				
				while(iterator.hasNext()) {
					UserUrl.setUserName((String) iterator.next());
					iUserUrl.addUserUrl(UserUrl);
					logger.info("用户 :" + iterator.next() + "已经存入数据库");
				}
			}
			
			Thread.sleep(1000);
		}
		
		
	}

	@Override
	public void getAllUserIDFromDB() throws InterruptedException {
	
		List<String> list = iUserUrl.queryAllUserName();
		logger.info("共从数据库获得" + list.size() + "条数据！");
		
		for(String name : list) {
			Collection.userIDQueue.put(name);
		}
		
		logger.info("UserIDQueue 中共有" + Collection.userIDQueue.size() + "条数据！");
	}

	@Override
	public void getAllUser() throws InterruptedException, IOException {
		int userThreadNum = Integer.parseInt(ConfigProperty.getInstance().getProperty("userThreadNum"));
		userPool = Executors.newFixedThreadPool(userThreadNum);
		int maxTotal = Integer.parseInt(ConfigProperty.getInstance().getProperty("maxTotal"));
		int defaultMaxPerRoute = Integer.parseInt(ConfigProperty.getInstance().getProperty("defaultMaxPerRoute"));
		CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(maxTotal, defaultMaxPerRoute);
		
		logger.info("UserID Queue Size:" + Collection.userIDQueue.size());
		
		int length = Collection.userIDQueue.size();
		String userUrl;
		
		for(int i = 0; i< length; i++) {
//			userUrl = "https://www.zhihu.com/people/" + Collection.userIDQueue.take() + "/about";
			userUrl = "https://www.zhihu.com/people/ma-qian-zu/about";
			HttpGet httpget = new HttpGet(userUrl);
			userPool.execute(new UserInfoHandler(httpClient, httpget));
			httpget.releaseConnection();
		}
		
		userPool.shutdown();
		
		while(true) {
			
			showPoolStatus(userPool, "UserName");
			
			if(userPool.isTerminated()) {
				httpClient.close();
				logger.info("所有的线程已经结束！");
				break;
			}
			
			Thread.sleep(1000);
		}
		
	}
	
	private void showPoolStatus(ExecutorService pool, String taskName) {
		
		queueCount = ((ThreadPoolExecutor)pool).getQueue().size();
		activeCount = ((ThreadPoolExecutor)pool).getActiveCount();
		completedTask = ((ThreadPoolExecutor)pool).getCompletedTaskCount();
		totalTask = ((ThreadPoolExecutor)pool).getTaskCount();
		
		logger.info("当前排队" + taskName + "个数为: " + queueCount);
		logger.info("当前运行" + taskName +"个数为: " + activeCount);
		logger.info("当前完成" + taskName + "个数为: " + completedTask);
		logger.info("总共" + taskName + "个数为： " + totalTask);
		
	}

}
