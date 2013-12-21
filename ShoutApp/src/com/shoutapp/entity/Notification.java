package com.shoutapp.entity;

import java.util.Date;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Notification {
	private int type, relatedid;
	private String message;
	private Date time;

	public Date getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public int getRelatedid() {
		return relatedid;
	}

	public int getType() {
		return type;
	}

	public static void getNotifications(String hash, Callback<Notification[]> c) {
		FetchJsonTask<Notification[]> u = new FetchJsonTask<Notification[]>(Notification[].class, "getNotifications", c);
		u.execute("hash", hash);
	}
}
