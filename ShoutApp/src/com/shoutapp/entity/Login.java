package com.shoutapp.entity;

import android.util.Log;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Login {
	public static void peform(String socialId, String deviceId, Callback<Login> c) {
		Log.d("socialId- deviceId", socialId+" - " + deviceId);
		FetchJsonTask<Login> u = new FetchJsonTask<Login>(Login.class, "register", c);
		u.execute("socialId", socialId, "deviceId", deviceId);
	}
	private int userId;
	//public String devideId;
	private String hash;

	public String getHash() {
		return hash;
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "userId: " + userId + " hash: " + hash;
	}
}
