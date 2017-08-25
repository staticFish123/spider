package com.casaba.spider.share;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Collection {
	
	private final static int topicID_length = 50;
	
	//topicID队列
	public final static BlockingQueue<String> topicIDQueue = new ArrayBlockingQueue<String>(topicID_length);
	
	

}
