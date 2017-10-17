package com.casaba.spider.model;

import java.io.Serializable;

public class UserUrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7122029174493131887L;

	private String userID;
	private String userName;
	private String searched;
	

	public UserUrl() {

	}
	
	public UserUrl(String userID, String userName, String searched) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.searched = searched;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearched() {
		return searched;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	
}
