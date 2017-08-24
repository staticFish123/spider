package com.casaba.spider.topic.impl;

import java.io.IOException;


import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.casaba.spider.topic.TopicService;

public class TopicServiceImpl implements TopicService {

	@Override
	public void getTopicID() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://www.zhihu.com/topics");
		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);

		//获取正文
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity, "UTF-8");
		
		//设置过滤条件
		String regex = "data-id=\"[0-9]{0,6}\"";
		

	}

}
