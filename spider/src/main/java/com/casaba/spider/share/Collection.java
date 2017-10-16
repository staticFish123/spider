package com.casaba.spider.share;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Collection {
	
	private final static int topicID_length = 50;
	
	private final static int subTopicID_length = 50000;
	
	private final static int userID_length = 500000;
	
	//topicID队列
	public final static BlockingQueue<String> topicIDQueue = new ArrayBlockingQueue<String>(topicID_length);
	
	//分topicID队列
	public final static BlockingQueue<String> subTopicIDQueue = new ArrayBlockingQueue<String>(subTopicID_length);
	
	//用户名队列
	public final static BlockingQueue<String> userIDQueue = new ArrayBlockingQueue<>(userID_length);

}
