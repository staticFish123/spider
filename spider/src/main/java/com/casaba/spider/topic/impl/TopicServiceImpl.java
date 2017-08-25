package com.casaba.spider.topic.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casaba.spider.share.Collection;
import com.casaba.spider.topic.TopicService;
import com.casaba.spider.utils.ConfigProperty;

public class TopicServiceImpl implements TopicService {
	
	private final static Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

	@Override
	public void getTopicID() throws ClientProtocolException, IOException {	
		HttpClient client = HttpClients.createDefault();
		String topicURL = ConfigProperty.getInstance().getProperty("topicURL");
		logger.info("Topic URL :" + topicURL);
		HttpGet get = new HttpGet(topicURL);
		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

		//获取正文
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");
		
		//设置过滤条件
		String regex = "data-id=\"[0-9]{0,6}\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(body);
		
		while(matcher.find()) {
			String s = matcher.group();
			System.out.println("data-id ="+s.substring(9, s.length()-1));
			Collection.topicIDQueue.add(s.substring(9, s.length()-1));
		}
		
		response.close();
		EntityUtils.consume(entity);

	}
	
	public static void main(String[] args) {
		TopicService service = new TopicServiceImpl();
		try {
			service.getTopicID();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
