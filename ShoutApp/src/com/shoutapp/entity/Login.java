package com.shoutapp.entity;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Login {
	private int userId;
	private String hash;

	public String getHash() {
		return hash;
	}

	public int getUserId() {
		return userId;
	}

	public static void peform(String socialId, String deviceId,
			Callback<Login> c) {
		FetchJsonTask<Login> u = new FetchJsonTask<Login>(Login.class,
				"register", c);
		u.execute("socialId", socialId, "deviceId", deviceId);
	}

	@Override
	public String toString() {
		return "userId: " + userId + " hash: " + hash;
	}
}
