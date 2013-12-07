package com.shoutapp;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

public class Model {
	
	public static String userName;
	public static String profile_pic_url;
	
	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;
	
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
//        comment_list.add(new NotificationItemObject("Receive a new Badge","","15:03"));
//        comment_list.add(new NotificationItemObject("Post a new comment on your Shout ...","Kate Austen","15:03"));
//        comment_list.add(new NotificationItemObject("Someone rate your Shout ...","","15:03"));
        return comment_list;
	}
	
	public static ArrayList<CommentItemObjet> getComments(){
		ArrayList<CommentItemObjet> comment_list = new ArrayList<CommentItemObjet>();
        comment_list.add(new CommentItemObjet("Az sabredersen, mercimek köftesi, ýslak kek ve dünden kalma biraz su böreði getirebilirim, evde çay var mý? :))","Kate Austen","15:03"));
        comment_list.add(new CommentItemObjet("Gün var orada sanýrým? :))","Ayþe Teyze","15:05"));
        comment_list.add(new CommentItemObjet("Günümüze herkes davetlidir? :))","Kate Austen","15:07"));
        comment_list.add(new CommentItemObjet("Acayip caným istedi? :))","Mr. Crowley","15:08"));
        comment_list.add(new CommentItemObjet("Terliklerimi çantama koydum 5 dakikaya oradayým.? :))","Leyla M.","15:12"));
        comment_list.add(new CommentItemObjet("Marketten meyve suyu alabilir misiniz?","Kate Austen","15:13"));
        return comment_list;
	}
	
	public static boolean add_post(String location,String category,String time,String duration, String title, String description){
		
		return true;
	}
	
	private static void refreshPostPreviews(){
		if(postPreviewItems == null)
			postPreviewItems = new ArrayList<PostPreviewItemObject>();
		int i = 1;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","05:00","14 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Parti","23:00","9 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Oyun","23:00","5 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Etkinlik","22:00","14 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Duyuru","21:10","4 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Sanat","21:00","7 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","20:00","13 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","19:00","12 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","18:09","14 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","18:00","9 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","17:30","1 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","17:00","9 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","16:00","9 km")); i++;
		postPreviewItems.add(new PostPreviewItemObject("Announcement "+i, "Spor","15:00","9 km")); i++;
		
		
		/*
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç", "Yemek","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç2", "Yemek","15:30","4 km"));
		*/
	}
	
	public static ArrayList<PostPreviewItemObject> getPostPreviews(){
		if(postPreviewItems == null)
			refreshPostPreviews();
		return postPreviewItems;
	}
	
	public static void addPostPreview(int index, PostPreviewItemObject postPreview){
		postPreviewItems.add(index,postPreview);
	}
	
}
