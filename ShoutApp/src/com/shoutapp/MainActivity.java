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


public class MainActivity extends BaseActivity{

	private Context cxt;
	Activity currentactivity;
	ViewPager pager;
	RefreshableListView postListView;

	public static GPSTracker gpsObject;
	//	private static ArrayList<PostPreviewItemObject> postPreviewItems = null;

	private GoogleMap map;
	//	ArrayList<PostPreviewItemObject> items;



	//	//googlecloudmessaging
	//	GoogleCloudMessaging gcm;
	//	 String regid;
	//	 Context context;
	//	 AtomicInteger msgId = new AtomicInteger();
	//	    SharedPreferences prefs;
	//	public static final String EXTRA_MESSAGE = "message";
	//    public static final String PROPERTY_REG_ID = "registration_id";
	//    private static final String PROPERTY_APP_VERSION = "appVersion";
	//    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	//        
	//    String SENDER_ID = "549547622167";
	//	
	//	private boolean checkPlayServices() {
	//	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	//	    if (resultCode != ConnectionResult.SUCCESS) {
	//	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	//	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	//	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	//	        } else {
	//	            Log.i("GCMDemo", "This device is not supported.");
	//	            finish();
	//	        }
	//	        return false;
	//	    }
	//	    return true;
	//	}
	//	/**
	//	 * Gets the current registration ID for application on GCM service.
	//	 * <p>
	//	 * If result is empty, the app needs to register.
	//	 *
	//	 * @return registration ID, or empty string if there is no existing
	//	 *         registration ID.
	//	 */
	//
	//	private String getRegistrationId(Context context) {
	//	    final SharedPreferences prefs = getGCMPreferences(context);
	//	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	//	    if (registrationId == null || registrationId == ""){//.isEmpty()) {
	//	        Log.i("GCMDemo", "Registration not found.");
	//	        return "";
	//	    }
	//	    // Check if app was updated; if so, it must clear the registration ID
	//	    // since the existing regID is not guaranteed to work with the new
	//	    // app version.
	//	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	//	    int currentVersion = getAppVersion(context);
	//	    if (registeredVersion != currentVersion) {
	//	        Log.i("GCMDemo", "App version changed.");
	//	        return "";
	//	    }
	//	    return registrationId;
	//	}
	//
	//	/**
	//	 * @return Application's {@code SharedPreferences}.
	//	 */
	//	private SharedPreferences getGCMPreferences(Context context) {
	//	    // This sample app persists the registration ID in shared preferences, but
	//	    // how you store the regID in your app is up to you.
	//	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	//	            Context.MODE_PRIVATE);
	//	}
	//	private static int getAppVersion(Context context) {
	//	    try {
	//	        PackageInfo packageInfo = context.getPackageManager()
	//	                .getPackageInfo(context.getPackageName(), 0);
	//	        return packageInfo.versionCode;
	//	    } catch (NameNotFoundException e) {
	//	        // should never happen
	//	        throw new RuntimeException("Could not get package name: " + e);
	//	    }
	//	}
	//	private void storeRegistrationId(Context context, String regId) {
	//	    final SharedPreferences prefs = getGCMPreferences(context);
	//	    int appVersion = getAppVersion(context);
	//	    Log.i("GCMDemo", "Saving regId on app version " + appVersion);
	//	    SharedPreferences.Editor editor = prefs.edit();
	//	    editor.putString(PROPERTY_REG_ID, regId);
	//	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	//	    editor.commit();
	//	}
	//	/**
	//	 * Registers the application with GCM servers asynchronously.
	//	 * <p>
	//	 * Stores the registration ID and app versionCode in the application's
	//	 * shared preferences.
	//	 */
	//	private void registerInBackground() {
	//	    new RegistrationClass().execute(null,null,null);
	//	}
	//	// GCM END


	@Override
	protected void onResume() {
		super.onResume();
		//checkPlayServices();
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

	OnClickListener profileClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(cxt, ProfileActivity.class);
			intent.putExtra("profileId", User.hash);			
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

	//	private class RegistrationClass extends AsyncTask<Void, Void, String> {
	//		
	//		
	//	        @Override
	//	        protected String doInBackground(Void... params) {
	//	            String msg = "";
	//	            try {
	//	                if (gcm == null) {
	//	                    gcm = GoogleCloudMessaging.getInstance(context);
	//	                }
	//	                regid = gcm.register(SENDER_ID);
	//	                msg = "Device registered, registration ID=" + regid;
	//
	//	                // You should send the registration ID to your server over HTTP,
	//	                // so it can use GCM/HTTP or CCS to send messages to your app.
	//	                // The request to your server should be authenticated if your app
	//	                // is using accounts.
	//	                sendRegistrationIdToBackend();
	//
	//	                // For this demo: we don't need to send it because the device
	//	                // will send upstream messages to a server that echo back the
	//	                // message using the 'from' address in the message.
	//
	//	                // Persist the regID - no need to register again.
	//	                storeRegistrationId(context, regid);
	//	            } catch (IOException ex) {
	//	                msg = "Error :" + ex.getMessage();
	//	                // If there is an error, don't just keep trying to register.
	//	                // Require the user to click a button again, or perform
	//	                // exponential back-off.
	//	            }
	//	            return msg;
	//	        }
	//
	//	        @Override
	//	        protected void onPostExecute(String msg) {
	//	           
	//	        }
	//	    
	//	    
	//	    /**
	//	     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	//	     * or CCS to send messages to your app. Not needed for this demo since the
	//	     * device sends upstream messages to a server that echoes back the message
	//	     * using the 'from' address in the message.
	//	     */
	//	    
	//	    private void sendRegistrationIdToBackend() {
	//	      // Your implementation here.
	//	    	
	//	    	// register olan makinanýn kodu neymiþ onu bi görek
	//	    	
	//	    	
	//	    }
	//	}

	// serverdan gelenler bu kodun içinde listeye eklenecek, ona göre modifiye et
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
						map.addMarker(new MarkerOptions().position(new LatLng(gpsObject.latitude, gpsObject.longitude)).title("You are here!"));
					}

					@Override
					public void callback_ack() {}
				});
				r.execute();
				postListView.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh(RefreshableListView listView) {
						// TODO Auto-generated method stub
						new NewDataTask().execute();
					}
				});
				collection.addView(v,position);
			}else{

				LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.post_map, null,false);
				collection.addView(v,position);
				gpsObject = new GPSTracker(MainActivity.this);

				final LatLng loc = new LatLng(gpsObject.latitude, gpsObject.longitude);
				map = ((MapFragment) currentactivity.getFragmentManager().findFragmentById(R.id.map)).getMap();
				map.addMarker(new MarkerOptions().position(loc).title("You are here!"));
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
				ImageButton swipe_btn = (ImageButton)v.findViewById(R.id.swipe_btn_map);
				swipe_btn.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {

						if(event.getAction() == MotionEvent.ACTION_UP){
							((BaseActivity)currentactivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//							return false;
						}else{// if(event.getAction() == MotionEvent.ACTION_DOWN) {
							SlidingMenu sm = ((BaseActivity)currentactivity).getSlidingMenu();
							sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
							sm.clearAnimation();
							sm.clearFocus();
//							SlidingMenu sm = ((BaseActivity)currentactivity).getSlidingMenu();
//							sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//							sm.clearAnimation();
//							sm.clearFocus();
//							MotionEventCompat me = new MotionEventCompat();
//							event.setAction(MotionEvent.ACTION_UP);
//							pager.dispatchTouchEvent(event);
						}
						
						return false;
					}
				});
				swipe_btn.bringToFront();
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
