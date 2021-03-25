package com.jweb.member;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable{

	private static final long serialVersionUID = 1000;
	
	private String memberId;
	private String passwd;
	private String name;
	private String gender;
	private Date joinDate;
	
	//public Member() {} -> 기본 생성자
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
}
