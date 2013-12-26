package com.shoutapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shoutapp.entity.Event;

public class EventPreviewAdapter extends ArrayAdapter<Event> {

	Context cxt;
	public static String getCategoryName(int id){
		if ( id == 1) return "Sport";
		if ( id == 2) return "Party";
		if ( id == 4) return "Game";
		if ( id == 8) return "Activity";
		if ( id == 16) return "Art";
		if ( id == 32) return "Other";
		return "Unknown category";
	}
	
	ListView rlv;
	private GoogleMap map;
	ArrayAdapter<Event> adapter;

	public EventPreviewAdapter(ListView listView, Context context, int textViewResourceId, Event[] list, GoogleMap map) {
		super(context, textViewResourceId, list);
		this.cxt = context;
		adapter = this;
		
		rlv = listView;
		this.map = map;
		if (map != null) {
			map.clear();
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick(Marker marker) {
					// TODO Auto-generated method stub
					String content = marker.getTitle();
					// ArrayList<String> items = (ArrayList<String>)
					// Arrays.asList(content.split(";"));
					String[] items = content.split(";");
					if (items.length > 1) {
						int position = Integer.parseInt(items[1]);
						Event obj = adapter.getItem(position);
						Intent intent = new Intent(cxt, PostItemViewActivity.class);

						intent.putExtra("eventId", obj.getId());
						intent.putExtra("owner", obj.getCreatorid());
						cxt.startActivity(intent);
					}
				}
			});
			map.setInfoWindowAdapter(new InfoWindowAdapter() {

				@Override
				public View getInfoContents(Marker marker) {

					return null;
				}

				@Override
				public View getInfoWindow(Marker marker) {
					String content = marker.getTitle();
					String[] items = content.split(";");
					if (items.length > 1) {
						int position = Integer.parseInt(items[1]);
						View convertView = adapter.getView(position, null, null);
						return convertView;
					}
					return null;
				}
			});
		}
		rlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Log.d("Position of event", "" + position);

				Event obj = (Event) arg0.getItemAtPosition(position + 1);
				Intent intent = new Intent(cxt, PostItemViewActivity.class);
				intent.putExtra("eventId", obj.getId());
				intent.putExtra("owner", obj.getCreatorid());
				cxt.startActivity(intent);
			}
		});
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItem(position).getTitle() == "") {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
			convertView.setVisibility(View.INVISIBLE);
		} else {
			Event e = getItem(position);
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
			TextView title = (TextView) convertView.findViewById(R.id.post_title);
			title.setText(e.getTitle());

			TextView categoryView = (TextView) convertView.findViewById(R.id.category);
			String category = getCategoryName(e.getCategory());
			categoryView.setText(category + "");

			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(e.getDateString());

			TextView distanceView = (TextView) convertView.findViewById(R.id.distance);
			String distance = e.distance(cxt);
			distanceView.setText(distance);
			if (map != null) {
				map.addMarker(new MarkerOptions().position(new LatLng(e.getLat(), e.getLon())).title("id;" + position));
			}
		}
		return convertView;

	}

}
