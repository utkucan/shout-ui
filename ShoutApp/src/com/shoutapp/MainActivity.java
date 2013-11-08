package com.shoutapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	
		
//		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View v = vi.inflate(R.layout.main_frame, null);

		// fill in any details dynamically here
//		RelativeLayout mainLay = (RelativeLayout)findViewById(R.id.mainLayout);
//		ListFragment postList = new PostListFragment();
//		mainLay.addView(postList.getView());
//		if (savedInstanceState == null){
//			RelativeLayout mainLay = (RelativeLayout)findViewById(R.id.mainLayout);
//			ListFragment postList = new PostListFragment();
//			mainLay.addView(postList.getView());
//			
//		}
		
//		TextView textView = (TextView) v.findViewById(R.id.a_text_view);
//		textView.setText("your text");
//
//		// insert into main view
//		View insertPoint = findViewById(R.id.insert_point);
//		((ViewGroup) insertPoint).addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//		
		ListFragment mFrag;
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new PostListFragment();
			t.replace(R.id.fragmentlayout, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.fragmentlayout);
		}
		
	}
	
	public static class PostListFragment extends ListFragment{
		
//		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//			return inflater.inflate(R.layout.post_list, null);
//		}
//		
//		public void onActivityCreated(Bundle savedInstanceState) {
//			super.onActivityCreated(savedInstanceState);
//			SampleAdapter adapter = new SampleAdapter(getActivity());
//			
//			adapter.add(new SampleItem("Karným Aç", "Yemek","15:00","14 km"));
//			adapter.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
//			setListAdapter(adapter);
//		}
//
//		private class SampleItem {
//			public String title;
//			public String category;
//			public String time;
//			public String distance;
//			public SampleItem(String title, String category,String time,String distance) {
//				this.title = title; 
//				this.category = category;
//				this.time = time; 
//				this.distance = distance;
//			}
//		}
//
//		public class SampleAdapter extends ArrayAdapter<SampleItem> {
//
//			public SampleAdapter(Context context) {
//				super(context, 0);
//			}
//
//			public View getView(int position, View convertView, ViewGroup parent) {
//				if (convertView == null) {
//					convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, null);
//				}
//				TextView title = (TextView) convertView.findViewById(R.id.post_title);
//				String a = getItem(position).title;
//				title.setText(getItem(position).title);
//				
//				TextView category = (TextView) convertView.findViewById(R.id.category);
//				String b = getItem(position).category;
//				category.setText(getItem(position).category);
//				
//				TextView time = (TextView) convertView.findViewById(R.id.time);
//				String c = getItem(position).time;
//				time.setText(getItem(position).time);
//				
//				TextView distance = (TextView) convertView.findViewById(R.id.distance);
//				String d = getItem(position).distance;
//				distance.setText(getItem(position).distance);
//
//				return convertView;
//			}
//
//		}
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.post_list, null);
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			SampleAdapter adapter = new SampleAdapter(getActivity());
			
			adapter.add(new SampleItem("Karným Aç", "Yemek","15:00","14 km"));
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
				title.setText(getItem(position).title);

				return convertView;
			}

		}
	}

}
