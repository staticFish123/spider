package com.casaba.spider.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.dao.IUserInfo;
import com.casaba.spider.model.UserInfo;

public class UserInfoTest extends BaseTest {
	
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("init.xml");
	private static IUserInfo iUserInfo = ctx.getBean(IUserInfo.class);
	
//	@Test
	public void addUserInfo() {
		UserInfo userInfo = new UserInfo("test", "male", "shanghai", "IT", "大学", "码农", "USST", "Automation", 2, 2, 3, 4, 5, 6, 7, 8, 9, "pingduoduo");
		int result = iUserInfo.addUserInfo(userInfo);
		assert(result == 1);
	}

}
