package com.casaba.spider.topic;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface TopicService {
	
	void getTopicID() throws ClientProtocolException, IOException;
	
	void getAllSubTopicID();
}
