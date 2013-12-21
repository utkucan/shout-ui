package com.shoutapp.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.shoutapp.entity.FetchJsonTask.Callback;

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
	
	public static void addComment(String hash, int eventId, String content, Callback<Status> c) {
		FetchJsonTask<Status> u = new FetchJsonTask<Status>(Status.class, "addComment", c);
		u.execute("hash", hash, "eventId", eventId, "content", content);
	}
}
