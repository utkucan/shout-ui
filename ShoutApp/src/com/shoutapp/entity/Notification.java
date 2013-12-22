package com.shoutapp.entity;

import java.util.Date;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Notification {
	public static void getNotifications(String hash, Callback<Notification[]> c) {
		FetchJsonTask<Notification[]> u = new FetchJsonTask<Notification[]>(Notification[].class, "getNotifications", c);
		u.execute("hash", hash);
	}
	private int type, relatedid;
	private String message;

	private Date time;

	public String getMessage() {
		return message;
	}

	public int getRelatedid() {
		return relatedid;
	}

	public Date getTime() {
		return time;
	}

	public int getType() {
		return type;
	}
}
