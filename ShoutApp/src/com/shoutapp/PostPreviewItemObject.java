package com.shoutapp;

public class PostPreviewItemObject {
	public String title;
	public String category;
	public String time;
	public String distance;
	public int eventId;
	public PostPreviewItemObject(String title, String category,String time,String distance,int eventId) {
		this.title = title; 
		this.category = category;
		this.time = time; 
		this.distance = distance;
		this.eventId = eventId;
	}
}
