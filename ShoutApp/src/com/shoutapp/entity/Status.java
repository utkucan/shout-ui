package com.shoutapp.entity;

public class Status {
	private String message;

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "Status: " + message;
	}
}
