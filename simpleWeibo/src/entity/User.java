package entity;

import java.util.Set;

public class User {
	private Integer userId;
	private String name;
	private String sex;
	private Integer age;
	private String email;
	private String phone;
	private String remark;//个人简介
	private String touxiang;//头像
	private Integer fansNum;
	private String password;
	private Integer focusPeopleNum;
	private Set<User> fans;//粉丝
	private Set<User> focusPeople; //关注人
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTouxiang() {
		return touxiang;
	}
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public int getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getFansNum() {
		return fansNum;
	}
	public void setFansNum(Integer fansNum) {
		this.fansNum = fansNum;
	}
	public Integer getFocusPeopleNum() {
		return focusPeopleNum;
	}
	public void setFocusPeopleNum(Integer focusPeopleNum) {
		this.focusPeopleNum = focusPeopleNum;
	}
	public Set<User> getFocusPeople() {
		return focusPeople;
	}
	public void setFocusPeople(Set<User> focusPeople) {
		this.focusPeople = focusPeople;
	}
	public Set<User> getFans() {
		return fans;
	}
	public void setFans(Set<User> fans) {
		this.fans = fans;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", sex=" + sex + ", age=" + age + ", email=" + email
				+ ", phone=" + phone + ", remark=" + remark + ", touxiang=" + touxiang + ", fansNum=" + fansNum
				+ ", focusPeopleNum=" + focusPeopleNum + ", fans=" + fans + ", focusPeople=" + focusPeople + "]";
	}
}
