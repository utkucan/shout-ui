package com.shoutapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

public class Model {
	
	public static String userName;
	public static String profile_pic_url;
	
	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;
	private static ArrayList<CommentItemObjet> comment_list = null; 
	
	public static ArrayList<BadgeObject> getBadge(){
		ArrayList<BadgeObject> badges = new ArrayList<BadgeObject>();
		badges.add(new BadgeObject(R.drawable.events_cat_bicycle, "01.01.2013", "Go Healthy"));
		badges.add(new BadgeObject(R.drawable.events_cat_camp, "23.05.2013", "Camper"));
		badges.add(new BadgeObject(R.drawable.events_cat_food, "09.12.2013", "Chronical Hungry"));
		badges.add(new BadgeObject(R.drawable.events_cat_game, "13.04.2013", "Game Addict"));
		badges.add(new BadgeObject(R.drawable.events_cat_movie, "20.01.2013", "Dr. Movie"));
		badges.add(new BadgeObject(R.drawable.events_cat_night, "17.11.2013", "Night watcher"));
		badges.add(new BadgeObject(R.drawable.events_cat_uknowwhatimean, "23.11.2013", "Socialize"));
		badges.add(new BadgeObject(R.drawable.events_cat_wood, "12.05.2013", "Green Activist"));
		badges.add(new BadgeObject(R.drawable.events_count_1, "12.11.2012", "First time"));
		badges.add(new BadgeObject(R.drawable.events_count_10, "23.01.2013", "Tenth first time"));
		badges.add(new BadgeObject(R.drawable.events_count_25, "11.03.2013", "25 Combo"));
		badges.add(new BadgeObject(R.drawable.events_count_50, "05.04.2013", "Half of hundred"));
		badges.add(new BadgeObject(R.drawable.events_count_100, "23.06.2012", "SHOUT!"));
		badges.add(new BadgeObject(R.drawable.events_count_250, "19.08.2013", "Leader"));
		badges.add(new BadgeObject(R.drawable.events_count_500, "05.10.2013", "Thats too much"));
		badges.add(new BadgeObject(R.drawable.events_count_king, "01.12.2013", "Celebrity"));
		return badges;
	}
	
	public static ArrayList<NotificationItemObject> getNotifications(){
		ArrayList<NotificationItemObject> comment_list = new ArrayList<NotificationItemObject>();
        comment_list.add(new NotificationItemObject("Receive a new Badge","","15:03",0));
        comment_list.add(new NotificationItemObject("Post a new comment on your Shout ...","Kate Austen","15:03",0));
        comment_list.add(new NotificationItemObject("Someone rate your Shout ...","","15:03",0));
        return comment_list;
	}
	
	
	public static boolean add_post(String location,String category,String time,String duration, String title, String description){
		
		return true;
	}
//	
//	private static void refreshPostPreviews(){
//		if(postPreviewItems == null)
//			postPreviewItems = new ArrayList<PostPreviewItemObject>();
//		final double lat = MainActivity.gpsObject.latitude;
//		final double lon = MainActivity.gpsObject.longitude;
//		SendLocation r = new SendLocation(User.hash,lat,lon, new RespCallback() {
//			
//			@Override
//			public void callback_events(ArrayList<Event> Events) {
//				// TODO Auto-generated method stub
//				for (Event e : Events) {
//					postPreviewItems.add(new PostPreviewItemObject(e.description + " " + e.title, e.category+"",
//							new SimpleDateFormat("MM/dd/yyyy hh:mm").format(e.creationDate),distance(e.latitute,e.longtitute,lat,lon)+" km",e.id));
//				}
////				MainActivity.refreshList();
//			}
//			
//			@Override
//			public void callback_ack() {
//				// TODO Auto-generated method stub
//			}
//		});
//		r.execute();
//		/*
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","05:00","14 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Parti","23:00","9 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Oyun","23:00","5 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Etkinlik","22:00","14 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Duyuru","21:10","4 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Sanat","21:00","7 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","20:00","13 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","19:00","12 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","18:09","14 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","18:00","9 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","17:30","1 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","17:00","9 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","16:00","9 km")); i++;
//		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","15:00","9 km")); i++;
//		*/
//		
//		/*
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç", "Yemek","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
//		*/
//	}
//	
//	public static ArrayList<PostPreviewItemObject> getPostPreviews(){
//		if(postPreviewItems == null)
//			refreshPostPreviews();
//		return postPreviewItems;
//	}
//	
//	public static void addPostPreview(int index, PostPreviewItemObject postPreview){
//		postPreviewItems.add(index,postPreview);
//	}
//	private static int distance(double lat1, double lon1, double lat2, double lon2) {
//	      double theta = lon1 - lon2;
//	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
//	      dist = Math.acos(dist);
//	      dist = rad2deg(dist);
//	      dist = dist * 60 * 1.1515;
//	      //if (unit == "K") {
//	        dist = dist * 1.609344;
//	      //} else if (unit == "N") {
//	       // dist = dist * 0.8684;
//	       // }
//	      return (int)(dist);
//	    }
//	    private static double deg2rad(double deg) {
//	      return (deg * Math.PI / 180.0);
//	    }
//	    private static double rad2deg(double rad) {
//	      return (rad * 180.0 / Math.PI);
//	    }

}
