package com.shoutapp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MarkerOptionsCreator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.shoutapp.RefreshableListView.OnRefreshListener;
import com.shoutapp.entity.FetchJsonTask.Callback;


public class MainActivity extends BaseActivity{

	private Context cxt;
	Activity currentactivity;
	ViewPager pager;
	RefreshableListView postListView;

	public static GPSTracker gpsObject;
	//	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;

	private GoogleMap map;
	//	ArrayList<PostPreviewItemObject> items;

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		gpsObject = new GPSTracker(MainActivity.this);
//		setSlidingActionBarEnabled(false);
		super.onCreate(savedInstanceState);
		cxt = this;
		currentactivity = this;
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		View swipe = LayoutInflater.from(getBaseContext()).inflate(R.layout.swipe, null);
		RelativeLayout swipeLayout = (RelativeLayout)swipe.findViewById(R.id.swipe_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		swipeLayout.setLayoutParams(lp);
		mainLayout.addView(swipe);
		pager = (ViewPager)swipe.findViewById(R.id.page_swiper);
		SwipePagerAdapter adapter = new SwipePagerAdapter();
		pager.setAdapter(adapter);

		RelativeLayout change_view_btn_holder = (RelativeLayout)findViewById(R.id.change_view_btn_holder);
		change_view_btn_holder.setOnClickListener(changeViewClickListener);
		ImageButton change_view_btn = (ImageButton)findViewById(R.id.change_view_btn);
		change_view_btn.setOnClickListener(changeViewClickListener);

		ImageButton add_post_btn = (ImageButton)findViewById(R.id.add_post_btn);
		add_post_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();

				i.setClassName("com.shoutapp", "com.shoutapp.AddPostActivity");
				startActivity(i);
			}
		});

		RelativeLayout profile_view_btn_holder = (RelativeLayout)findViewById(R.id.profile_btn_holder);
		profile_view_btn_holder.setOnClickListener(profileClickListener);
		ImageButton profile_view_btn = (ImageButton)findViewById(R.id.profile_btn);
		profile_view_btn.setOnClickListener(profileClickListener);

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				((BaseActivity)currentactivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			}
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        // do something on back.
	    	if(pager.getCurrentItem()==1){
	    		pager.setCurrentItem(0, true);
				ImageButton btn = (ImageButton)findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
	    	}
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}

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
			// TODO Auto-generated method stub
			if(pager.getCurrentItem()==0){
				pager.setCurrentItem(1, true);
				ImageButton btn = (ImageButton)v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.menu_icon);
			}else{
				pager.setCurrentItem(0, true);
				ImageButton btn = (ImageButton)v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
			}
		}
	};

		
	// serverdan gelenler bu kodun içinde listeye eklenecek, ona göre modifiye et
	/*
	private class NewDataTask extends AsyncTask<Void, Void, Event> {

		@Override
		protected Event doInBackground(Void... params) {
			SendLocation r = new SendLocation(User.hash,gpsObject.latitude,gpsObject.longitude,new RespCallback() {

				@Override
				public void callback_events(ArrayList<Event> Events) {
					postListView.setAdapter(new EventPreviewAdapter(postListView,cxt, R.id.post_list_view, Events,map));
					map.addMarker(new MarkerOptions().position(new LatLng(gpsObject.latitude, gpsObject.longitude)).title("You are here!"));
				}

				@Override
				public void callback_ack() {
					// TODO Auto-generated method stub
				}
			});
			r.execute();
			return null;
		}

		@Override
		protected void onPostExecute(Event result) {
			postListView.completeRefreshing();
			super.onPostExecute(result);
		}
	}
	 */
	public class SwipePagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			View v;
			if(position == 0){
				v = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_list_layout, null);
				postListView = (RefreshableListView)v.findViewById(R.id.post_list_view);

				SendLocation r = new SendLocation(User.hash,gpsObject.latitude,gpsObject.longitude,new RespCallback() {

					@Override
					public void callback_events(ArrayList<Event> Events) {
						// TODO Auto-generated method stub
//						for (Event e : Events) {
//							map.addMarker(new MarkerOptions().position(new LatLng(e.latitute, e.longtitute)).title(e.title + ";"+e.category));
//						}
						postListView.setAdapter(new EventPreviewAdapter(postListView,cxt, R.id.post_list_view, Events,map));
						map.setMyLocationEnabled(true);
					//map.addMarker(new MarkerOptions().position(new LatLng(gpsObject.latitude, gpsObject.longitude)).title("You are here!"));
					}

					@Override
					public void callback_ack() {}
				});
				r.execute();
				postListView.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh(RefreshableListView listView) {
						// TODO Auto-generated method stub
						//new NewDataTask().execute();
						SendLocation r = new SendLocation(User.hash,gpsObject.latitude,gpsObject.longitude,new RespCallback() {

							@Override
							public void callback_events(ArrayList<Event> Events) {
								postListView.setAdapter(new EventPreviewAdapter(postListView,cxt, R.id.post_list_view, Events,map));
								map.setMyLocationEnabled(true);
//								map.addMarker(new MarkerOptions().position(new LatLng(gpsObject.latitude, gpsObject.longitude)).title("You are here!"));
								postListView.completeRefreshing();
							}

							@Override
							public void callback_ack() {
								// TODO Auto-generated method stub
							}
						});
						r.execute();
					}
				});
				com.shoutapp.entity.Event.fetchNearbyEventList(User.hash, gpsObject.latitude, gpsObject.longitude, new Callback<com.shoutapp.entity.Event[]>() {

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(com.shoutapp.entity.Event[] obj) {
						Log.d("Recieved:", obj.length + " events");
						for(com.shoutapp.entity.Event e: obj){
							Log.d("Event:", e.toString());
						}
					}

					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						
					}					
				} );
				collection.addView(v,position);
			}else{

				LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.post_map, null,false);
				collection.addView(v,position);
				gpsObject = new GPSTracker(MainActivity.this);

				final LatLng loc = new LatLng(gpsObject.latitude, gpsObject.longitude);
				map = ((MapFragment) currentactivity.getFragmentManager().findFragmentById(R.id.map)).getMap();
//				map.addMarker(new MarkerOptions().position(loc).title("You are here!"));
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//				ImageButton swipe_btn = (ImageButton)v.findViewById(R.id.swipe_btn_map);
//				swipe_btn.setOnTouchListener(new OnTouchListener() {
//					
//					@Override
//					public boolean onTouch(View v, MotionEvent event) {
//
//						if(event.getAction() == MotionEvent.ACTION_UP){
//							((BaseActivity)currentactivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
////							return false;
//						}else{// if(event.getAction() == MotionEvent.ACTION_DOWN) {
//							SlidingMenu sm = ((BaseActivity)currentactivity).getSlidingMenu();
//							sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//							sm.clearAnimation();
//							sm.clearFocus();
////							SlidingMenu sm = ((BaseActivity)currentactivity).getSlidingMenu();
////							sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
////							sm.clearAnimation();
////							sm.clearFocus();
////							MotionEventCompat me = new MotionEventCompat();
////							event.setAction(MotionEvent.ACTION_UP);
////							pager.dispatchTouchEvent(event);
//						}
//						
//						return false;
//					}
//				});
//				swipe_btn.bringToFront();
//				swipe_btn_map
			}
			return v;
		}
		
		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((TextView) view);
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view==object);
		}
		
		@Override
		public void finishUpdate(ViewGroup arg0) {
			if(pager.getCurrentItem()==0){
				ImageButton btn = (ImageButton)findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
			}else{
				ImageButton btn = (ImageButton)findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.menu_icon);
			}
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(ViewGroup arg0) {}

	}
}
