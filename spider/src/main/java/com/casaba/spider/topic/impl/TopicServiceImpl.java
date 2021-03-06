package com.casaba.spider.topic.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.casaba.spider.factory.HttpClientFactory;
import com.casaba.spider.share.Collection;
import com.casaba.spider.threads.topicHandler;
import com.casaba.spider.topic.TopicService;
import com.casaba.spider.utils.ConfigProperty;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

	private final static Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

	private static ExecutorService pool;

	@Override
	public void getTopicID() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		String topicURL = ConfigProperty.getInstance().getProperty("topicURL");
		// logger.info("Topic URL :" + topicURL);
		HttpGet get = new HttpGet(topicURL);
		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

		// 获取正文
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");

		// 设置过滤条件
		String regex = "data-id=\"[0-9]{0,6}\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(body);

		while (matcher.find()) {
			String s = matcher.group();
			System.out.println("data-id =" + s.substring(9, s.length() - 1));
			Collection.topicIDQueue.add(s.substring(9, s.length() - 1));
		}

		logger.info("TopID Queue Size:" + Collection.topicIDQueue.size());
		response.close();
		EntityUtils.consume(entity);

	}

	@Override
	public void getAllSubTopicID() throws IOException, InterruptedException {
		// 建立线程池
		int threadNum = Integer.parseInt(ConfigProperty.getInstance().getProperty("topicThreadNum"));
		pool = Executors.newFixedThreadPool(threadNum);
		int maxTotal = Integer.parseInt(ConfigProperty.getInstance().getProperty("maxTotal"));
		int defaultMaxPerRoute = Integer.parseInt(ConfigProperty.getInstance().getProperty("defaultMaxPerRoute"));
		CloseableHttpClient httpClient = HttpClientFactory.getHttpClient(maxTotal, defaultMaxPerRoute);
		logger.info("TopicID Queue Size: " + Collection.topicIDQueue.size());

		int len = Collection.topicIDQueue.size();
		for (int i = 0; i < len; i++) {

			String url = ConfigProperty.getInstance().getProperty("subTopicURL");
			HttpPost httpPost = new HttpPost(url);

			pool.execute(new topicHandler(httpClient, httpPost, Collection.topicIDQueue.poll()));
			httpPost.releaseConnection();
		}

		pool.shutdown();

		while (true) {
			if (pool.isTerminated()) {
				httpClient.close();
				logger.info("所有的线程都已经结束了， 一共获取到SubTopicID个数为" + Collection.subTopicIDQueue.size() + "个");
				break;
			}
			Thread.sleep(1000);
		}

	}

}
