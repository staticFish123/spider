package com.casaba.spider.threads;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

public class topicHandler extends Thread {

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
		
	}
	
}
