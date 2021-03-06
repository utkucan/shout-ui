package com.shoutapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shoutapp.RefreshableListView.OnRefreshListener;
import com.shoutapp.entity.Event;
import com.shoutapp.entity.FetchJsonTask.Callback;


public class MainActivity extends BaseActivity {

	public class SwipePagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((TextView) view);
		}

		@Override
		public void finishUpdate(ViewGroup arg0) {
			if (pager.getCurrentItem() == 0) {
				ImageButton btn = (ImageButton) findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
			} else {
				ImageButton btn = (ImageButton) findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.menu_icon);
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			View v;
			if (position == 0) {
				v = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_list_layout, null);
				postListView = (RefreshableListView) v.findViewById(R.id.post_list_view);
				//fetchEvents();
				postListView.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh(RefreshableListView listView) {
						Log.d("On refresh", "called");
						fetchEvents();
					}
				});

				collection.addView(v, position);
			} else {

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.post_map, null, false);
				collection.addView(v, position);
				gpsObject = new GPSTracker(MainActivity.this);

				final LatLng loc = new LatLng(gpsObject.latitude, gpsObject.longitude);
				map = ((MapFragment) currentactivity.getFragmentManager().findFragmentById(R.id.map)).getMap();
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));
				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			}
			return v;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(ViewGroup arg0) {
		}

	}
	private Context cxt;
	Activity currentactivity;
	ViewPager pager;

	RefreshableListView postListView;

	public static GPSTracker gpsObject;

	private GoogleMap map;

	OnClickListener profileClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(cxt, ProfileActivity.class);
			intent.putExtra("profileId", User.user_id);
			cxt.startActivity(intent);
		}
	};

	OnClickListener changeViewClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (pager.getCurrentItem() == 0) {
				pager.setCurrentItem(1, true);
				ImageButton btn = (ImageButton) v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.menu_icon);
			} else {
				pager.setCurrentItem(0, true);
				ImageButton btn = (ImageButton) v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
			}
		}
	};

	private void fetchEvents() {
		
		Event.fetchNearbyEventList(User.hash, gpsObject.latitude, gpsObject.longitude, new Callback<Event[]>() {

			@Override
			public void onFail() {
				Log.d("Event:", "eventler �ekilirken s�k�nt� oldu");
			}

			@Override
			public void onStart() {
				// postListView.startRefreshing(); Bu niye olmad� :(
			}

			@Override
			public void onSuccess(com.shoutapp.entity.Event[] events) {
				Log.d("Recieved: ", events.length + " events");
				for (Event e : events) {
					// Log.d("Event:", e.toString());
					map.addMarker(new MarkerOptions().position(new LatLng(e.getLat(), e.getLon())).title(e.getTitle() + ";" + e.getCategory()));
				}

				postListView.setAdapter(new EventPreviewAdapter(postListView, cxt, R.id.post_list_view, events, map));
				map.setMyLocationEnabled(true);
				postListView.completeRefreshing();
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		gpsObject = new GPSTracker(MainActivity.this);
		// setSlidingActionBarEnabled(false);
		super.onCreate(savedInstanceState);
		cxt = this;
		currentactivity = this;
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		
		View swipe = LayoutInflater.from(getBaseContext()).inflate(R.layout.swipe, null);
		RelativeLayout swipeLayout = (RelativeLayout) swipe.findViewById(R.id.swipe_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		lp.addRule(RelativeLayout.BELOW, R.id.topBar);
		swipeLayout.setLayoutParams(lp);
		mainLayout.addView(swipe);
		pager = (ViewPager) swipe.findViewById(R.id.page_swiper);
		SwipePagerAdapter adapter = new SwipePagerAdapter();
		pager.setAdapter(adapter);

		RelativeLayout change_view_btn_holder = (RelativeLayout) findViewById(R.id.change_view_btn_holder);
		change_view_btn_holder.setOnClickListener(changeViewClickListener);
		ImageButton change_view_btn = (ImageButton) findViewById(R.id.change_view_btn);
		change_view_btn.setOnClickListener(changeViewClickListener);
		
		
		/*start background service to periodically send the location */
    	Log.d("com.shoutapp.service", "start service");
		Intent intent = new Intent(this, PeriodicService.class);
    	startService(intent);
		
		ImageButton add_post_btn = (ImageButton) findViewById(R.id.add_post_btn);
		add_post_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.shoutapp", "com.shoutapp.AddPostActivity");
				startActivity(i);
			}
		});

		RelativeLayout profile_view_btn_holder = (RelativeLayout) findViewById(R.id.profile_btn_holder);
		profile_view_btn_holder.setOnClickListener(profileClickListener);
		ImageButton profile_view_btn = (ImageButton) findViewById(R.id.profile_btn);
		profile_view_btn.setOnClickListener(profileClickListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			if (pager.getCurrentItem() == 1) {
				pager.setCurrentItem(0, true);
				ImageButton btn = (ImageButton) findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
				return true;
			}		
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		SharedPreferences sp =  getSharedPreferences(LoginActivity.SAVEHASH, 0);
		User.user_id = sp.getInt("userid", -1);
		User.hash = sp.getString("hashval", null);
		super.onResume();
		fetchEvents();
	}
}
