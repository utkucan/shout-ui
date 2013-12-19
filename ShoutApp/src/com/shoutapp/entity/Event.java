package com.shoutapp.entity;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.shoutapp.entity.FetchJsonTask.Callback;

public class Event {
	private int id;
	private double lat, lon;
	private int category;
	
	@SerializedName("creator")
	private int userId;
	
	private String title;
	
	@SerializedName("creation")
	private Date creationTime;
	
	@SerializedName("expire")
	private Date expireTime;
	
	private Comment[] comments;

	public Comment[] getComments() {
		return comments;
	}

	public int getCategory() {
		return category;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public int getId() {
		return id;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public String getTitle() {
		return title;
	}

	public int getUserId() {
		return userId;
	}
	
	@Override
	public String toString() {
		return id + " " + title + " "  + lat + " " + lon;
	}

	public static void fetchNearbyEventList(String hash, double lat, double lon, Callback<Event[]> c) {
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class,
				"getNearbyEvents", c);
		u.execute("hash", hash, "lat", lat, "lon", lon);
	}
}
