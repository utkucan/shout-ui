package com.shoutapp.entity;

public class Comment {
	private int userId;
	private String name;
	private String content;

	public String getContent() {
		return content;
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "@" + name + " : " + content;
	}
}
