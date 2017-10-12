package com.casaba.spider;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.topic.TopicService;
import com.casaba.spider.user.UserService;

/**
 * Main Function
 *
 */

public class App extends BaseTest{

	private final static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	@Qualifier("topicService")
	private TopicService topicService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Test
	public void start() {
		try {

			//获取用户名
			topicService.getTopicID();
			topicService.getAllSubTopicID();
			userService.getAllUserURL();
			
			//获取用户信息
			
			
			

		} catch (IOException e) {
			logger.error("APP IOException", e);
		} catch (InterruptedException e) {
			logger.error("App InterruptedException", e);
		}

	}

}
