package com.casaba.spider.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;

public class UserUrlTest extends BaseTest{
	
//	@Autowired
//	private IUserUrl iUserUrl;
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("init.xml");
	private static IUserUrl iUserUrl = ctx.getBean(IUserUrl.class);
	
//	@Test
	@Transactional
	public void addUserUrl() {
		UserUrl userUrl = new UserUrl();
		userUrl.setUserName("lol");
		int result = iUserUrl.addUserUrl(userUrl);
		assert(result == 1);
	}
	
//	@Test
	@Transactional
	public void queryAllUserUrl() {
//		List<UserUrl> list = iUserUrl.queryAllUserUrl();
//		assert(null != list);
	}
	
//	@Test
	public void queryAllUserName() {
		List<String> list = new ArrayList<>();
		list = iUserUrl.queryAllUserName();
		assert(list != null);
	}
	
}
