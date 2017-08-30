package com.casaba.spider.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;

public class UserUrlTest extends BaseTest{
	
	@Autowired
	private IUserUrl iUserUrl;
	
	@Test
	public void addUserUrl() {
		UserUrl userUrl = new UserUrl();
		userUrl.setUserName("Test");
		int result = iUserUrl.addUserUrl(userUrl);
		assert(result == 1);
	}
}
