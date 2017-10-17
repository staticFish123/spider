package com.casaba.spider.model;

public class UserInfo {
	
	private String name;
	private String sex;
	private String location;
	private String business;
	private String employment;
	private String position;
	private String education;
	private String educationExtra;
	private int suppose; //赞同
	private int thanks; //感谢
	private int question;
	private int answer;
	private int article; //文章
	private int followers;
	private int following;
	private int shared; //被分享
	private int collected; //被收藏
	private String spellName; //知乎URL上的名字
	
	public UserInfo() {
		
	}
	
	public UserInfo(String name, String sex, String location, String business, String employment, String position,
			String education, String educationExtra, int suppose, int thanks, int question, int answer, int article,
			int followers, int following, int shared, int collected, String spellName) {
		super();
		this.name = name;
		this.sex = sex;
		this.location = location;
		this.business = business;
		this.employment = employment;
		this.position = position;
		this.education = education;
		this.educationExtra = educationExtra;
		this.suppose = suppose;
		this.thanks = thanks;
		this.question = question;
		this.answer = answer;
		this.article = article;
		this.followers = followers;
		this.following = following;
		this.shared = shared;
		this.collected = collected;
		this.spellName = spellName;
	}
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
	public String getEducationExtra() {
		return educationExtra;
	}
	public void setEducationExtra(String educationExtra) {
		this.educationExtra = educationExtra;
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
	public String getSpellName() {
		return spellName;
	}
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	@Override
	public String toString() {
		return "UserInfo [name=" + name + "]";
	}
	
}
