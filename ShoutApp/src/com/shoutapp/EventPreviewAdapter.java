package com.shoutapp;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	public EventPreviewAdapter(ListView listView,Context context, int textViewResourceId,ArrayList<Event> list) {
		super(context, textViewResourceId,list);
		this.cxt = context;
		categorys = new ArrayList<String>(Arrays.asList(context.getResources().getStringArray(R.array.Categories)));
		rlv = listView;
		rlv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0,
					View arg1, int position, long arg3) {
				// TODO Auto-generated method stub
				Event obj = (Event)arg0.getItemAtPosition(position+1);
				Intent intent = new Intent(cxt, PostItemViewActivity.class);
				intent.putExtra("title", obj.title);
				intent.putExtra("category", categorys.get(obj.category));
				intent.putExtra("time", obj.time);
				intent.putExtra("distance", obj.distance(cxt)+" km");
				intent.putExtra("eventId", obj.id);
				intent.putExtra("lat", obj.latitute);
				intent.putExtra("lon", obj.longtitute);
				intent.putExtra("description", obj.description);
				intent.putExtra("owner", obj.creator_id);
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
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
			TextView title = (TextView) convertView.findViewById(R.id.post_title);
			title.setText(getItem(position).title);

			TextView category = (TextView) convertView.findViewById(R.id.category);
			category.setText(categorys.get(getItem(position).category));

			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(getItem(position).time);

			TextView distance = (TextView) convertView.findViewById(R.id.distance);
			distance.setText(getItem(position).distance(cxt) + " km");
		}
		return convertView;
		
	}

}
