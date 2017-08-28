package com.casaba.spider.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casaba.spider.share.Collection;
import com.casaba.spider.utils.Hash;

public class topicHandler extends Thread {

	private final static Logger logger = LoggerFactory.getLogger(topicHandler.class);

	private CloseableHttpClient httpClient;
	private HttpContext context;
	private String topicID;
	private HttpPost httpPost;
	private int count;

	public topicHandler(CloseableHttpClient httpClient, HttpPost httpPost, String topicID) {
		this.httpClient = httpClient;
		this.topicID = topicID;
		this.httpClient = httpClient;
		this.context = HttpClientContext.create();
		this.httpPost = httpPost;
		this.topicID = topicID;
		this.count = 0;
	}

	@Override
	public void run() {

		logger.info("Thread:" + topicID + "Start...");

		Integer offSet = 0;

		while (true) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("method", "next"));
			String hashID = Hash.getHashID();
			String p2 = "{\"topic_id\":" + topicID + ",\"offset\":" + offSet + ",\"hash_id\":\"" + hashID + "\"}";
			logger.info("P2" + p2);
			params.add(new BasicNameValuePair("params", p2));
			UrlEncodedFormEntity entityPost = new UrlEncodedFormEntity(params, Consts.UTF_8);
			httpPost.setEntity(entityPost);

			try {

				CloseableHttpResponse response = httpClient.execute(httpPost, context);
				HttpEntity entity = response.getEntity();

				try {
					if (entity != null) {

						String body = EntityUtils.toString(entity, "UTF-8");

						if (body.length() < 100) {
							logger.info("TopicID" + topicID + "Finshed");
							break;
						}

						String regex = "topic..[0-9]{1,10}";
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(body);

						while (matcher.find()) {

							String s = matcher.group();
							logger.info("subTopicID:" + s.substring(7));

							count++;
							Collection.subTopicIDQueue.add(s.substring(7));

						}

						logger.info("Topic ID" + topicID + "offSet" + offSet);
						offSet = offSet + 20;

					}
				} finally {
					EntityUtils.consume(entity);
					response.close();
				}
			} catch (IOException e) {
				logger.error("IO Exception", e);
			}

		}
		logger.info("Thread----------------" + topicID + "Count:" + count);

	}

}
