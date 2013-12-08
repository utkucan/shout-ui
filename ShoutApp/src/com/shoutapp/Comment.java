package com.shoutapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
	String content;
	String userName;
	String userId;
	private Date creationTime;
	public Comment(String id, String userName, String content/*,Date time*/){
		this.content = content;
		this.userName =userName;
		this.userId = id;
		creationTime = new Date(System.currentTimeMillis());
	}
	
	public String getTime(){
		long diff = System.currentTimeMillis() - creationTime.getTime();
		if(diff>24*60*60*1000){
			return new SimpleDateFormat("dd/MM/yy hh:mm").format(creationTime);
		}else{
			return new SimpleDateFormat("hh:mm").format(creationTime);
		}
	}
	
	public String toString(){
		return content + " by " + userName;
	}
}
