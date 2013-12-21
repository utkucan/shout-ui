package com.shoutapp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	private int userid;
	private String name;
	private String content;
	private Date date;

	public Date getDate() {
		return date;
	}

	public String getDateString() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return fmt.format(date);
	}

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
