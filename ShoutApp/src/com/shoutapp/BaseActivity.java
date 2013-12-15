package com.shoutapp;

import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class BaseActivity extends SlidingMenuBaseActivity/*Activity */{
	//	public PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;
	private Activity base_activity;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	public static final String FILTER_PREFS = "filterPrefs";

	//	OnGoogleConnectionStateChangeEventListener gListener;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		base_activity = this;
		setSlidingActionBarEnabled(true);
		setContentView(R.layout.main_frame);

		RelativeLayout slidingMenuBtnHolder = (RelativeLayout)findViewById(R.id.slidingMenuButtonHolder);
		slidingMenuBtnHolder.setOnClickListener(slidingMenuClickListener);
		ImageButton slidingMenuBtn = (ImageButton)findViewById(R.id.slidingMenuButton);
		slidingMenuBtn.setOnClickListener(slidingMenuClickListener);

		RelativeLayout filterButtonHolder = (RelativeLayout)findViewById(R.id.filterButtonHolder);

		//filtre butonuna basýlýnca bu kod calýþacak inþallah

		filterButtonHolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				readPrefs(v);
				RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
				View seek = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_seekbar, null);
				SeekbarActivity sa = new SeekbarActivity(seek);
				RelativeLayout seekLayout = (RelativeLayout)seek.findViewById(R.id.seek_layout);

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.FILL_PARENT,
						RelativeLayout.LayoutParams.FILL_PARENT);
				seekLayout.setLayoutParams(lp);
				mainLayout.addView(seek);

				Button filterDoneBtn = (Button) findViewById(R.id.filter_save_btn);
				filterDoneBtn.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						//TODO Auto-generated method stub						 
						savePrefs();						
						RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
						View seek = (View) findViewById(R.id.seek_layout);
						mainLayout.removeView(seek);

					}
				});
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
	public void readPrefs(View v){
		SharedPreferences settings = getSharedPreferences(FILTER_PREFS, 0);
		int silent = settings.getInt("distance",-1);
		Toast.makeText(v.getContext(), "Shouts within "+ silent +"kms will be shown!",Toast.LENGTH_SHORT).show();
	}

	public void savePrefs(){
		//save all variables
		SharedPreferences settings = getSharedPreferences(FILTER_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("distance", ((SeekBar) findViewById(R.id.distance_bar)).getProgress());
		editor.putInt("timeMin", ((SeekBar) findViewById(R.id.clock_bar)).getProgress());
		//editor.putInt("timeMax", ((SeekBar) findViewById(R.id.distance_bar)).getProgress());

		editor.putBoolean("sports", ((CheckBox) findViewById(R.id.sportCheckBox)).isChecked());
		editor.putBoolean("party", ((CheckBox) findViewById(R.id.partyCheckBox)).isChecked());
		editor.putBoolean("game", ((CheckBox) findViewById(R.id.gameCheckBox)).isChecked());
		editor.putBoolean("activity", ((CheckBox) findViewById(R.id.activityCheckBox)).isChecked());
		editor.putBoolean("art", ((CheckBox) findViewById(R.id.artCheckBox)).isChecked());
		editor.putBoolean("other", ((CheckBox) findViewById(R.id.otherCheckBox)).isChecked());
		StringBuilder sb = new StringBuilder();
		if(((CheckBox) findViewById(R.id.sportCheckBox)).isChecked())
			sb.append("1;");
		if(((CheckBox) findViewById(R.id.partyCheckBox)).isChecked())
			sb.append("2;");
		if(((CheckBox) findViewById(R.id.gameCheckBox)).isChecked())
			sb.append("3;");
		if(((CheckBox) findViewById(R.id.activityCheckBox)).isChecked())
			sb.append("4;");
		if(((CheckBox) findViewById(R.id.artCheckBox)).isChecked())
			sb.append("5;");
		if(((CheckBox) findViewById(R.id.otherCheckBox)).isChecked())
			sb.append("6;");
		
		String prefStr = sb.toString();
		String last = prefStr.substring(0,prefStr.lastIndexOf(';'));
			Log.d("TRIM", sb.toString() +" -> "+ last);	
		editor.commit();
		(new SubmitReferences(last,
				((SeekBar) findViewById(R.id.distance_bar)).getProgress(),
				((SeekBar) findViewById(R.id.clock_bar)).getProgress(),
				new RespCallback() {

			@Override
			public void callback_events(ArrayList<Event> Events) {
				// TODO Auto-generated method stub
			}

			@Override
			public void callback_ack() {
				
			}
		})).execute();
	}

	OnClickListener slidingMenuClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			readPrefs(v);
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
