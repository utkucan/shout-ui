package com.shoutapp;

import java.util.ArrayList;

public class Model {

	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;
	
	public static boolean add_post(String location,String category,String time,String duration, String title, String description){
		
		return true;
	}
	
	private static void refreshPostPreviews(){
		if(postPreviewItems == null)
			postPreviewItems = new ArrayList<PostPreviewItemObject>();
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�", "Yemek","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�", "Yemek","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
		postPreviewItems.add(new PostPreviewItemObject("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		postPreviewItems.add(new PostPreviewItemObject("Karn�m A�2", "Yemek","15:30","4 km"));
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
