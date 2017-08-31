package com.casaba.spider.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;

public class UserUrlTest extends BaseTest{
	
	@Autowired
	private IUserUrl iUserUrl;
	
//	@Test
	@Transactional
	public void addUserUrl() {
		UserUrl userUrl = new UserUrl();
		userUrl.setUserName("lol");
		int result = iUserUrl.addUserUrl(userUrl);
		assert(result == 1);
	}
	
	@Test
	@Transactional
	public void queryAllUserUrl() {
		List<UserUrl> list = iUserUrl.queryAllUserUrl();
		assert(null != list);
	}
}
