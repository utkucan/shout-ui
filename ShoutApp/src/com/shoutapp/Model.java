package com.shoutapp;

import java.util.ArrayList;

public class Model {

	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;
	
	public static ArrayList<BadgeObject> getBadge(){
		ArrayList<BadgeObject> badges = new ArrayList<BadgeObject>();
		badges.add(new BadgeObject(R.drawable.events_cat_bicycle, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_camp, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_food, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_game, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_movie, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_night, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_uknowwhatimean, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_cat_wood, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_1, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_10, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_25, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_50, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_100, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_250, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_500, "23.11.2013", "hobarey"));
		badges.add(new BadgeObject(R.drawable.events_count_king, "23.11.2013", "hobarey"));
		return badges;
	}
	
	public static ArrayList<CommentItemObjet> getComments(){
		ArrayList<CommentItemObjet> comment_list = new ArrayList<CommentItemObjet>();
        comment_list.add(new CommentItemObjet("Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        comment_list.add(new CommentItemObjet("asdasdas Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        comment_list.add(new CommentItemObjet("Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        comment_list.add(new CommentItemObjet("Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        comment_list.add(new CommentItemObjet("Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        comment_list.add(new CommentItemObjet("Az sabredersen,  menemen ve dünden kalma biraz makarna getirebilirim, evde ekmek var mý? :))","Kate Austen"));
        return comment_list;
	}
	
	public static boolean add_post(String location,String category,String time,String duration, String title, String description){
		
		return true;
	}
	
	private static void refreshPostPreviews(){
		if(postPreviewItems == null)
			postPreviewItems = new ArrayList<PostPreviewItemObject>();
		postPreviewItems.add(new PostPreviewItemObject("Karným Aç", "Yemek","15:00","14 km"));
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
