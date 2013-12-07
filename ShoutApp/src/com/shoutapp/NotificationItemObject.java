package com.shoutapp;

public class NotificationItemObject {
	public String notificationText;
	public String owner;
	public String time;
	int relatedEventId;
	public NotificationItemObject(String notificationText, String owner,String time, int relatedEventId) {
		this.notificationText = notificationText; 
		this.owner = owner;
		this.time= time;
		this.relatedEventId = relatedEventId;
	}
}
