package com.casaba.spider.dao;

import java.util.List;

import com.casaba.spider.model.UserUrl;

public interface IUserUrl {
	List<UserUrl> queryAllUserUrl();
	int addUserUrl(UserUrl userUrl);
}
