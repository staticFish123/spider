package com.casaba.spider.test;

import org.junit.Test;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.utils.ConnectUtil;

public class ConnectTest extends BaseTest{
	
	@Test
	public void TestConnected() {
		boolean connected = ConnectUtil.isConnect();
		assert(connected == true);
	}

}
