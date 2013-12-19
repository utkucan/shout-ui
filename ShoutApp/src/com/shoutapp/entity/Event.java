package com.shoutapp.entity;

import com.shoutapp.entity.FetchJsonTask.Callback;

public class Event {
	private int id;
	private double lat, lon;
	private int category;
	private int userId;
	private int title;
	private long creationTime;
	private long expireTime;
	private Comment[] comments;

	public int getCategory() {
		return category;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getExpireTime() {
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

	public int getTitle() {
		return title;
	}

	public int getUserId() {
		return userId;
	}
	
	public void fetchNearbyEventList(double lat, double lon, Callback<Event[]> c){
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class, "submitLocation",
				c);
		u.execute("lat", lat, "lon", lon);
	}
	
	
}
