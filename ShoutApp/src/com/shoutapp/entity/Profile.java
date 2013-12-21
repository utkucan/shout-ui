package com.shoutapp.entity;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Profile {
	private String name, picture, location;
	private double lat, lon;
	private int popularity;

	public int getPopularity() {
		return popularity;
	}

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
	

	public static void getProfile(int userId, Callback<Profile> c) {
		FetchJsonTask<Profile> u = new FetchJsonTask<Profile>(Profile.class, "getProfile", c);
		u.execute("userId", userId);
	}
	
	public static void submitPreferences(String hash, Callback<Status> c){

	}
}
