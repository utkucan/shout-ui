package com.shoutapp.entity;

import com.shoutapp.entity.FetchJsonTask;
import com.shoutapp.entity.FetchJsonTask.Callback;

public class User {
	private String name;
	private int id;

	@Override
	public String toString() {
		return name + " " + id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static void fetch(int id, Callback<User> c) {
		FetchJsonTask<User> u = new FetchJsonTask<User>(User.class, "getUser",
				c);
		u.execute("id", id);
	}
}
