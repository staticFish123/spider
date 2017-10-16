package com.casaba.spider.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.casaba.spider.model.UserUrl;

@Repository
public interface IUserUrl {
	List<UserUrl> queryAllUserUrl();
	int addUserUrl(UserUrl userUrl);
	List<String> queryAllUserName();
}
