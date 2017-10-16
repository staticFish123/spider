package com.casaba.spider.user;

import java.io.IOException;

public interface UserService {
	
	void getAllUserURL() throws IOException, InterruptedException;
	
	void getAllUserIDFromDB() throws InterruptedException;
	
	void getAllUser() throws InterruptedException, IOException;
}
