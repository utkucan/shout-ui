package com.shoutapp;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shoutapp.GPSTracker;
import com.shoutapp.entity.Event;
import com.shoutapp.entity.FetchJsonTask.Callback;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class PeriodicService extends Service{

	private static final String TAG = "com.shoutapp.service";
	Timer myTimer;
	MyTimerTask myTask;
	double prevLat = 0, prevLong = 0;
	private GPSTracker gps;
	private static final int SEND_PERIOD_IN_SECONDS = 5 * 60;
	
	@Override
	public void onCreate() {
		Log.d(TAG, "Service onCreate");
		myTask = new MyTimerTask();
		myTimer = new Timer();
		gps = new GPSTracker(getBaseContext());
		
		SharedPreferences sp =  getSharedPreferences(LoginActivity.SAVEHASH, 0);
		User.user_id = sp.getInt("userid", -1);
		User.hash = sp.getString("hashval", null);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences sp =  getSharedPreferences(LoginActivity.SAVEHASH, 0);
		User.user_id = sp.getInt("userid", -1);
		User.hash = sp.getString("hashval", null);

		Log.i(TAG, "Service onStartCommand");
		try {
		myTimer.schedule(myTask, 3000, 1000 * SEND_PERIOD_IN_SECONDS); 
		gps = new GPSTracker(getBaseContext());
		} catch ( Exception ex){
			Log.d("Service error!", ex.getMessage());
		}
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Service onBind");
		return null;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Service onDestroy");
	}

	class MyTimerTask extends TimerTask {
		public void run() {
			if(gps != null && gps.latitude != prevLat || gps.longitude != prevLong){
				prevLat = gps.latitude; 
				prevLong = gps.longitude;

				Event.fetchNearbyEventList(User.hash, gps.latitude, gps.longitude, new Callback<Event[]>() {

					@Override
					public void onFail() {
						Log.d("Event:", "eventler çekilirken sýkýntý oldu");
					}
 
					@Override
					public void onStart() {
					} 

					@Override
					public void onSuccess(com.shoutapp.entity.Event[] events) {
						Log.d(TAG, "submitted location");
					}
				});
				Log.d(TAG, "periodic event, loc: " + gps.latitude + " " + gps.longitude);
			}
		}
	}


}