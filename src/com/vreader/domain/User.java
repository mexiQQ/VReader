package com.vreader.domain;

public class User {

	private String username;
	private String userid;
	private String userPictureUrl;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", userid=" + userid
				+ ", userPictureUrl=" + userPictureUrl + "]";
	}

	public String getUserPictureUrl() {
		return userPictureUrl;
	}

	public void setUserPictureUrl(String userPictureUrl) {
		this.userPictureUrl = userPictureUrl;
	}

	public User(String username, String userid, String userPictureUrl) {
		this.username = username;
		this.userid = userid;
		this.userPictureUrl = userPictureUrl;
	}
	
	public User() {
		
	}
}
