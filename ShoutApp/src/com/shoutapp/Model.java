package com.shoutapp;

import java.util.ArrayList;

public class Model {

	public static boolean add_post(String location,String category,String time,String duration, String title, String description){
		
		return true;
	}
	
	public ArrayList<PostPreview> getPostPreviews(){
		ArrayList<PostPreview> items = new ArrayList<PostPreview>();
		items.add(new PostPreview("Karn�m A�", "Yemek","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new PostPreview("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new PostPreview("Karn�m A�2", "Yemek","15:30","4 km"));
		return items;
	}

	public class PostPreview {
		public String title;
		public String category;
		public String time;
		public String distance;
		public PostPreview(String title, String category,String time,String distance) {
			this.title = title; 
			this.category = category;
			this.time = time; 
			this.distance = distance;
		}
	}
	
}
