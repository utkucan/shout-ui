package com.shoutapp.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.shoutapp.GPSTracker;
import com.shoutapp.entity.FetchJsonTask.Callback;

public class Event {
	public static void fetchEventDetails(int id, Callback<Event> c) {
		FetchJsonTask<Event> u = new FetchJsonTask<Event>(Event.class, "getEvent", c);
		u.execute("id", id);
	}

	public static void fetchEventsOfUser(int userId, Callback<Event[]> c) {
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class, "getUserEvents", c);
		u.execute("userId", userId);
	}

	public static void fetchNearbyEventList(String hash, double lat, double lon, Callback<Event[]> c) {
		FetchJsonTask<Event[]> u = new FetchJsonTask<Event[]>(Event[].class, "getNearbyEvents", c);
		u.execute("hash", hash, "lat", lat, "lon", lon);
	}

	public static void removeEvent(String hash, int eventId, Callback<Status> c) {
		FetchJsonTask<Status> u = new FetchJsonTask<Status>(Status.class, "removeEvent", c);
		u.execute("hash", hash, "eventId", eventId);
	}

	public static void submitEvent(String hash, double lat, double lon, int category, String title, String description, Date creation, Date expire,
			Callback<Status> c) {
		FetchJsonTask<Status> u = new FetchJsonTask<Status>(Status.class, "submitEvent", c);
		u.execute("hash", hash, "lat", lat, "lon", lon, "category", category, "title", title, "description", description, "creation", creation,
				"expire", expire);
	}

	private int id;

	private double lat, lon;

	private int category;

	private int creatorid;

	private String creatorname;

	private String title;

	private String description;

	@SerializedName("creation")
	private Date creationTime;

	@SerializedName("expire")
	private Date expireTime;

	private Comment[] comments;

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public String distance(Context cxt) {

		GPSTracker gpsObject = new GPSTracker(cxt);
		double lat2 = gpsObject.getLatitude();
		double lon2 = gpsObject.getLongitude();

		double theta = lon - lon2;
		double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		if (dist < 1) {
			dist = dist * 1000;
			return String.format("%s m", (int) dist);
		} else {
			return String.format("%.2f km", dist);
		}
	}

	public int getCategory() {
		return category;
	}

	public Comment[] getComments() {
		return comments;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public int getCreatorid() {
		return creatorid;
	}

	public String getCreatorName() {
		return creatorname;
	}

	public String getDateString() {
		long timestamp = creationTime.getTime();
		long now = System.currentTimeMillis();
		final long DAY = 24 * 60 * 1000;
		if (Math.abs(now - timestamp) > DAY) {
			DateFormat df = new SimpleDateFormat("EEE HH:mm");
			return df.format(creationTime);
		} else {
			DateFormat df = new SimpleDateFormat("HH:mm");
			return df.format(creationTime);
		}
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

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	@Override
	public String toString() {
		return id + " " + title + " " + lat + " " + lon + (comments == null || comments.length == 0 ? " No comments" : comments[0]);
	}
}
