package com.shoutapp;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EventPreviewAdapter extends ArrayAdapter<Event> {

	Context cxt;
	ArrayList<String> categorys;
	ListView rlv;
	private GoogleMap map;
	ArrayAdapter<Event> adapter;


	public EventPreviewAdapter(ListView listView,Context context, int textViewResourceId,ArrayList<Event> list,GoogleMap map) {
		super(context, textViewResourceId,list);
		this.cxt = context;
		adapter = this;
		categorys = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.Categories)));
		rlv = listView;
		this.map = map;
		if(map != null){
			map.clear();
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker marker) {
					// TODO Auto-generated method stub
					String content = marker.getTitle();
					//					ArrayList<String> items = (ArrayList<String>) Arrays.asList(content.split(";"));
					String[] items = content.split(";");
					if(items.length>1){
						int position = Integer.parseInt(items[1]);
						Event obj = adapter.getItem(position);
						Intent intent = new Intent(cxt, PostItemViewActivity.class);
						intent.putExtra("eventId", obj.id);
						intent.putExtra("owner", obj.creator_id);
						cxt.startActivity(intent);
					}
				}
			});
			map.setInfoWindowAdapter(new InfoWindowAdapter() {

				@Override
				public View getInfoWindow(Marker marker) {
					// TODO Auto-generated method stub
					String content = marker.getTitle();
					//					ArrayList<String> items = (ArrayList<String>) Arrays.asList(content.split(";"));
					String[] items = content.split(";");
					if(items.length>1){

						int position = Integer.parseInt(items[1]);
						View convertView = adapter.getView(position, null, null);

//						convertView.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								Event obj = adapter.getItem(rlv.getPositionForView(v)-1);
//								Intent intent = new Intent(cxt, PostItemViewActivity.class);
//								intent.putExtra("eventId", obj.id);
//								intent.putExtra("owner", obj.creator_id);
//								cxt.startActivity(intent);
//							}
//						});
						return convertView;
					}
					return null;
				}

				@Override
				public View getInfoContents(Marker marker) {
					// TODO Auto-generated method stub

					//					String content = marker.getTitle();
					//					//					ArrayList<String> items = (ArrayList<String>) Arrays.asList(content.split(";"));
					//					String[] items = content.split(";");
					//					if(items.length>1){
					//
					//						int position = Integer.parseInt(marker.getTitle());
					//						View convertView = adapter.getView(position, null, null);
					//						
					//						convertView.setOnClickListener(new OnClickListener() {
					//
					//							@Override
					//							public void onClick(View v) {
					//								// TODO Auto-generated method stub
					//								Event obj = adapter.getItem(rlv.getPositionForView(v)-1);
					//								Intent intent = new Intent(cxt, PostItemViewActivity.class);
					//								intent.putExtra("eventId", obj.id);
					//								intent.putExtra("owner", obj.creator_id);
					//								cxt.startActivity(intent);
					//							}
					//						});
					//
					//						
					//						
					//						
					//						
					////						View convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
					////						TextView title = (TextView) convertView.findViewById(R.id.post_title);
					////						title.setText(items[0]);
					////
					////						TextView category = (TextView) convertView.findViewById(R.id.category);
					////						category.setText(items[1]);
					////
					////						TextView time = (TextView) convertView.findViewById(R.id.time);
					////						time.setText(items[2]);
					////
					////						TextView distance = (TextView) convertView.findViewById(R.id.distance);
					////						distance.setText(items[3]);
					//						return convertView;
					//					}
					return null;
				}
			});
		}
		rlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,
					View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Event obj = (Event)arg0.getItemAtPosition(position+1);
				Intent intent = new Intent(cxt, PostItemViewActivity.class);
				intent.putExtra("eventId", obj.id);
				intent.putExtra("owner", obj.creator_id);

				//				intent.putExtra("title", obj.title);
				//				intent.putExtra("category", categorys.get(obj.category));
				//				intent.putExtra("time", obj.time);
				//				intent.putExtra("distance", obj.distance(cxt)+" km");
				//				intent.putExtra("lat", obj.latitute);
				//				intent.putExtra("lon", obj.longtitute);
				//				intent.putExtra("description", obj.description);

				cxt.startActivity(intent);
			}
		});
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(getItem(position).title == ""){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
			convertView.setVisibility(View.INVISIBLE);
		}
		else{
			Event e = getItem(position);
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
			TextView title = (TextView) convertView.findViewById(R.id.post_title);
			title.setText(e.title);

			TextView categoryView = (TextView) convertView.findViewById(R.id.category);
			String category = categorys.get(e.category);
			categoryView.setText(category+"");

			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(e.time);

			TextView distanceView = (TextView) convertView.findViewById(R.id.distance);
			String distance = e.distance(cxt) + " km";
			distanceView.setText(distance);
			if(map != null){
				map.addMarker(new MarkerOptions().position(new LatLng(e.latitute, e.longtitute)).title("id;"+position));//e.title + ";"+category+";"+e.time+";"+distance));
			}

			//			convertView.setOnClickListener(new OnClickListener() {
			//				
			//				@Override
			//				public void onClick(View v) {
			//					// TODO Auto-generated method stub
			//					Event obj = adapter.getItem(rlv.getPositionForView(v)-1);
			//					Intent intent = new Intent(cxt, PostItemViewActivity.class);
			//					intent.putExtra("eventId", obj.id);
			//					intent.putExtra("owner", obj.creator_id);
			//					cxt.startActivity(intent);
			//				}
			//			});

		}
		return convertView;

	}

}
