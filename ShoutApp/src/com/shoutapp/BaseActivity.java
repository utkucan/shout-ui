package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
=======
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
>>>>>>> 8079a7aaa50ad3d82151d362ebf189cf1d47c8e0
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BaseActivity extends SlidingMenuBaseActivity{
<<<<<<< HEAD
//	public PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;
	private Activity base_activity;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
//	OnGoogleConnectionStateChangeEventListener gListener;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		base_activity = this;
//		mPlusClient = new PlusClient.Builder(this, gp_connectionCallback, gp_OnConnectionFailedListener).setVisibleActivities("http://schemas.google.com/AddActivity","http://schemas.google.com/ListenActivity").build();
=======
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
>>>>>>> 8079a7aaa50ad3d82151d362ebf189cf1d47c8e0
		setSlidingActionBarEnabled(true);
		setContentView(R.layout.main_frame);
		
		RelativeLayout slidingMenuBtnHolder = (RelativeLayout)findViewById(R.id.slidingMenuButtonHolder);
		slidingMenuBtnHolder.setOnClickListener(slidingMenuClickListener);
		ImageButton slidingMenuBtn = (ImageButton)findViewById(R.id.slidingMenuButton);
		slidingMenuBtn.setOnClickListener(slidingMenuClickListener);
		
		RelativeLayout filterButtonHolder = (RelativeLayout)findViewById(R.id.filterButtonHolder);
		
		// filtre butonuna basýlýnca bu kod calýþacak inþallah
		filterButtonHolder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent();
//		        i.setClassName("com.shoutapp", "com.shoutapp.SeekbarActivity");
//		        startActivity(i);
				
//				RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
//		        View seek = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_seekbar, null);
//		        RelativeLayout seekLayout = (RelativeLayout)seek.findViewById(R.id.seek_layout);
//				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//													      RelativeLayout.LayoutParams.FILL_PARENT,
//													      RelativeLayout.LayoutParams.FILL_PARENT);
////		        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//		        seekLayout.setLayoutParams(lp);
//		        mainLayout.addView(seek);
		        
				RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
				View seek = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_seekbar, null);
				SeekbarActivity sa = new SeekbarActivity(seek);
		        RelativeLayout seekLayout = (RelativeLayout)seek.findViewById(R.id.seek_layout);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
													      RelativeLayout.LayoutParams.FILL_PARENT,
													      RelativeLayout.LayoutParams.FILL_PARENT);
//		        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		        seekLayout.setLayoutParams(lp);
		        mainLayout.addView(seek);
		        
			}
		});
		
		ImageView logo = (ImageView)findViewById(R.id.titleLogo);
		logo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!v.getContext().getClass().equals(MainActivity.class)){
					Intent intent = new Intent(getBaseContext(), MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
					startActivity(intent);
				}
			}
		});
	}
	
	
	OnClickListener slidingMenuClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SlidingMenu sm = getSlidingMenu();
			sm.toggle(true);
		}
	};
	
//	private ConnectionCallbacks gp_connectionCallback = new ConnectionCallbacks() {
//		
//		@Override
//		public void onDisconnected() {
//			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
//			startActivity(intent);
//			gListener.onDisconnect();
//		}
//		
//		private Bitmap getImageBitmap(String url) {
//	        Bitmap bm = null;
//	        try {
//	            URL aURL = new URL(url);
//	            URLConnection conn = aURL.openConnection();
//	            conn.connect();
//	            InputStream is = conn.getInputStream();
//	            BufferedInputStream bis = new BufferedInputStream(is);
//	            bm = BitmapFactory.decodeStream(bis);
//	            bis.close();
//	            is.close();
//	       } catch (IOException e) {
////	           Log.e(TAG, "Error getting bitmap", e);
//	       }
//	       return bm;
//	    }
		
//		@Override
//		public void onConnected(Bundle connectionHint) {
//			Person user = mPlusClient.getCurrentPerson();
//			if(user != null){
//				Intent intent = new Intent(getBaseContext(), MainActivity.class);
////				intent.putExtra("loginType", "g");
//				startActivity(intent);
//				gListener.onConnect();
//			}else{
//				mPlusClient.disconnect();
//			}
//		}
//	};
//	private OnConnectionFailedListener gp_OnConnectionFailedListener = new OnConnectionFailedListener() {
//		
//		@Override
//		public void onConnectionFailed(ConnectionResult result) {
//			if (mConnectionProgressDialog.isShowing()) {
//	               if (result.hasResolution()) {
//	                       try {
//	                               result.startResolutionForResult(base_activity, REQUEST_CODE_RESOLVE_ERR);
//	                       } catch (SendIntentException e) {
//	                               mPlusClient.connect();
//	                       }
//	               }
//	       }
//			mConnectionResult = result;
//		}
//	};
//	
//	public void setOnGoogleConnectionStateChangeEvent(OnGoogleConnectionStateChangeEventListener listener){
//		gListener = listener;
//	}
//	
//	public interface OnGoogleConnectionStateChangeEventListener{
//		public void onConnect();
//		public void onDisconnect();
//	}
}
