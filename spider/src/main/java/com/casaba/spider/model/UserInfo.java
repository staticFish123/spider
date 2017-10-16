package com.casaba.spider.model;

public class UserInfo {
	
	private String name;
	private String sex;
	private String location;
	private String business;
	private String employment;
	private String position;
	private String education;
	private String education_extra;
	private int suppose; //赞同
	private int thanks; //感谢
	private int question;
	private int answer;
	private int article; //文章
	private int followers;
	private int following;
	private int shared; //被分享
	private int collected; //被收藏
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getEmployment() {
		return employment;
	}
	public void setEmployment(String employment) {
		this.employment = employment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEducation_extra() {
		return education_extra;
	}
	public void setEducation_extra(String education_extra) {
		this.education_extra = education_extra;
	}
	public int getSuppose() {
		return suppose;
	}
	public void setSuppose(int suppose) {
		this.suppose = suppose;
	}
	public int getThanks() {
		return thanks;
	}
	public void setThanks(int thanks) {
		this.thanks = thanks;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}
	public int getAnswer() {
		return answer;
	}
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	public int getArticle() {
		return article;
	}
	public void setArticle(int article) {
		this.article = article;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public int getShared() {
		return shared;
	}
	public void setShared(int shared) {
		this.shared = shared;
	}
	
	public int getCollected() {
		return collected;
	}
	public void setCollected(int collected) {
		this.collected = collected;
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + "]";
	}
	
}
