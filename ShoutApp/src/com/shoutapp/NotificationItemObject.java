package com.shoutapp;

public class NotificationItemObject {
	public String notificationText;
	public String owner;
	public String time;
	public NotificationItemObject(String notificationText, String owner,String time) {
		this.notificationText = notificationText; 
		this.owner = owner;
		this.time= time;
	}
}
