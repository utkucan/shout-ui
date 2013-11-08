package com.shoutapp;

import java.util.ArrayList;

import com.shoutapp.SlidingMenuBaseActivity.MenuListFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	
		ListFragment mFrag;
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new PostListFragment();
			t.replace(R.id.fragmentlayout, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.fragmentlayout);
		}
		
		
		
//		ListView lv = (ListView)findViewById(R.id.listView);
//		ArrayList<SampleItem> items = new ArrayList<MainActivity.SampleItem>();
//		items.add(new SampleItem("Karným Aç", "Yemek","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
//		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//		lv.setAdapter(new SampleAdapter(this, R.id.listView, items));

	}
	
	
	
//	private class SampleItem {
//		public String title;
//		public String category;
//		public String time;
//		public String distance;
//		public SampleItem(String title, String category,String time,String distance) {
//			this.title = title; 
//			this.category = category;
//			this.time = time; 
//			this.distance = distance;
//		}
//	}
//
//	public class SampleAdapter extends ArrayAdapter<SampleItem> {
//        
//		public SampleAdapter(Context context, int textViewResourceId,
//                ArrayList list) {
//			super(context, textViewResourceId,list);
//			
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, null);         
//			}
//			TextView title = (TextView) convertView.findViewById(R.id.post_title);
//			String a = getItem(position).title;
//			title.setText(getItem(position).title);
//			
//			TextView category = (TextView) convertView.findViewById(R.id.category);
//			String b = getItem(position).category;
//			category.setText(getItem(position).category);
//			
//			TextView time = (TextView) convertView.findViewById(R.id.time);
//			String c = getItem(position).time;
//			time.setText(getItem(position).time);
//			
//			TextView distance = (TextView) convertView.findViewById(R.id.distance);
//			String d = getItem(position).distance;
//			distance.setText(getItem(position).distance);
//
//			return convertView;
//		}
//
//	}

	
	
	
	
	
	public static class PostListFragment extends ListFragment{
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.post_list, null);
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			SampleAdapter adapter = new SampleAdapter(getActivity());
			
			adapter.add(new SampleItem("Karným Aç", "Yemek","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			adapter.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
			setListAdapter(adapter);
		}

		private class SampleItem {
			public String title;
			public String category;
			public String time;
			public String distance;
			public SampleItem(String title, String category,String time,String distance) {
				this.title = title; 
				this.category = category;
				this.time = time; 
				this.distance = distance;
			}
		}

		public class SampleAdapter extends ArrayAdapter<SampleItem> {

			public SampleAdapter(Context context) {
				super(context, 0);
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, null);
				}
				TextView title = (TextView) convertView.findViewById(R.id.post_title);
				String a = getItem(position).title;
				title.setText(getItem(position).title);
				
				TextView category = (TextView) convertView.findViewById(R.id.category);
				String b = getItem(position).category;
				category.setText(getItem(position).category);
				
				TextView time = (TextView) convertView.findViewById(R.id.time);
				String c = getItem(position).time;
				time.setText(getItem(position).time);
				
				TextView distance = (TextView) convertView.findViewById(R.id.distance);
				String d = getItem(position).distance;
				distance.setText(getItem(position).distance);

				return convertView;
			}

		}
	}

}
