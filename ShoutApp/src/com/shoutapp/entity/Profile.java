package com.shoutapp.entity;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Profile {
	public static void getProfile(int userId, Callback<Profile> c) {
		FetchJsonTask<Profile> u = new FetchJsonTask<Profile>(Profile.class, "getProfile", c);
		u.execute("userId", userId);
	}
	public static void submitPreferences(String hash, int categoryMask, int distance, int time, Callback<Status> c) {
		FetchJsonTask<Status> u = new FetchJsonTask<Status>(Status.class, "submitPreferences", c);
		u.execute("hash", hash, "category", categoryMask, "distance", distance, "time", time);
	}
	private String name, picture, location;

	private double lat, lon;

	private int popularity;

	public double getLat() {
		return lat;
	}

	public String getLocation() {
		return location;
	}

	public double getLon() {
		return lon;
	}

	public String getName() {
		return name;
	}

	public String getPicture() {
		return picture;
	}

	public int getPopularity() {
		return popularity;
	}
}
