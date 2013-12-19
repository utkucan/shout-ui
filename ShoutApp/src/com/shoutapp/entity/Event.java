package com.shoutapp.entity;

import java.util.Date;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.shoutapp.GPSTracker;
import com.shoutapp.entity.FetchJsonTask.Callback;

public class Event {
	private int id;
	private double lat, lon;
	private int category;

	private int creatorid;

	public int getCreatorid() {
		return creatorid;
	}

	private String creator;

	private String title;

	private String description;

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

	public String getDescription() {
		return description;
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

	public String getCreator() {
		return creator;
	}

	@Override
	public String toString() {
		return id + " " + title + " " + lat + " " + lon + (comments == null || comments.length == 0 ? " No comments" : comments[0]);
	}

	public static void fetchNearbyEventList(String hash, double lat, double lon, Callback<Event[]> c) {
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class, "getNearbyEvents", c);
		u.execute("hash", hash, "lat", lat, "lon", lon);
	}

	public static void fetchEventDetails(int id, Callback<Event> c) {
		FetchJsonTask<Event> u = new FetchJsonTask<Event>(Event.class, "getEvent", c);
		u.execute("id", id);
	}

	public static void fetchEventsOfUser(int userId, Callback<Event[]> c) {
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class, "getUserEvents", c);
		u.execute("userId", userId);
	}

	public static void submitEvent(String hash, double lat, double lon, int category, String title, String description, Date creation, Date expire, Callback<Status> c) {
		FetchJsonTask<Status> u = new FetchJsonTask<Status>(Status.class, "submitEvent", c);
		u.execute("hash", hash, "lat", lat, "lon", lon, "category", category, "title", title, "description", description, "creation", creation, "expire", expire);
	}

	public int distance(Context cxt) {

		GPSTracker gpsObject = new GPSTracker(cxt);
		double lat2 = gpsObject.getLatitude();
		double lon2 = gpsObject.getLongitude();

		double theta = lon - lon2;
		double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (int) (dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}
