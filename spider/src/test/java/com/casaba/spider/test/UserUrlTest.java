package com.casaba.spider.test;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.casaba.spider.base.BaseTest;
import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;
import com.casaba.spider.utils.SpringContextUtil;

public class UserUrlTest extends BaseTest{
	
//	@Autowired
//	private IUserUrl iUserUrl;
	
	@Test
	@Transactional
	public void addUserUrl() {
		UserUrl userUrl = new UserUrl();
		userUrl.setUserName("lol");
		IUserUrl iUserUrl = SpringContextUtil.getBean(IUserUrl.class);
		int result = iUserUrl.addUserUrl(userUrl);
		assert(result == 1);
	}
	
//	@Test
	@Transactional
	public void queryAllUserUrl() {
//		List<UserUrl> list = iUserUrl.queryAllUserUrl();
//		assert(null != list);
	}
	
}
