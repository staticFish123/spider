package com.casaba.spider;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.casaba.spider.topic.TopicService;
import com.casaba.spider.user.UserService;

/**
 * Main Function
 *
 */
public class App {

	private final static Logger logger = LoggerFactory.getLogger(App.class);

	@Autowired
	@Qualifier("topicService")
	private static TopicService topicService;

	@Autowired
	@Qualifier("userService")
	private static UserService userService;

	public static void main(String[] args) {
		try {

			topicService.getTopicID();
			topicService.getAllSubTopicID();

			userService.getAllUserURL();

		} catch (IOException e) {
			logger.error("APP IOException", e);
		} catch (InterruptedException e) {
			logger.error("App InterruptedException", e);
		}

	}

}
