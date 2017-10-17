package com.casaba.spider.dao;

import org.springframework.stereotype.Repository;

import com.casaba.spider.model.UserInfo;

@Repository
public interface IUserInfo {
	int addUserInfo(UserInfo userInfo);
}
