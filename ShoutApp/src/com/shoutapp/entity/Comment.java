package com.shoutapp.entity;

public class Comment {
	private int userid;
	private String name;
	private String content;

	public String getContent() {
		return content;
	}

	public int getUserId() {
		return userid;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "@" + name + " : " + content;
	}
}
