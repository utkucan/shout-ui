package com.shoutapp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.android.gms.maps.model.Marker;

import android.content.Context;
import android.text.method.DateTimeKeyListener;


public class Event {

	int id, category;
	double longtitute, latitute, radius;
	Date creationDate, expiredDate;
	String title,description,creator_id;
	ArrayList<Comment> comments;
	String time;

	public Event(String title, double longtitute, double latitute, double radius, Date creationDate, Date expiredDate,
			int cat, String creator){
		this.title = title;
		this.longtitute = longtitute;
		this.latitute = latitute;
		this.radius = radius;
		this.creationDate = creationDate;
		this.expiredDate = expiredDate;	
		category = cat;
		creator_id = creator; 
		description = "";
		comments = new ArrayList<Comment>();
		if(creationDate !=null){
			Date today = new Date(System.currentTimeMillis());
			if(creationDate.after(today) || (creationDate.before(today) && expiredDate.after(today)))
				time = new SimpleDateFormat("hh:mm").format(creationDate);
			else{
				time = new SimpleDateFormat("MM/dd/yy").format(creationDate);
				time += "\n"+new SimpleDateFormat("hh:mm").format(creationDate);
			}

		}
	}

	void setId (int id){
		this.id = id;
	}

	public String toString(){
		String s = title + "\n";
		for(Comment c: comments){
			s += c + "\n";
		}
		return s ; // +..+.. + (TODO)
	}

	public void setDesc(String desc) {
		description = desc;

	}
	public void addComment(Comment c){
		comments.add(c);
	}

	public int distance(Context cxt) {

		GPSTracker gpsObject = new GPSTracker(cxt);
		double lat2 = gpsObject.latitude;
		double lon2 = gpsObject.longitude;

		double theta = longtitute - lon2;
		double dist = Math.sin(deg2rad(latitute)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(latitute)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		//if (unit == "K") {
		dist = dist * 1.609344;
		//} else if (unit == "N") {
		// dist = dist * 0.8684;
		// }
		return (int)(dist);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
	
	public Marker getMarker(){
		return null;
	}

}
