package com.shoutapp;

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

import com.google.android.gms.common.ConnectionResult;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shoutapp.entity.FetchJsonTask.Callback;
import com.shoutapp.entity.Profile;
import com.shoutapp.entity.Status;

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
		editor.commit();
		
		int value = 0;
		if(((CheckBox) findViewById(R.id.sportCheckBox)).isChecked())
			value += 1;
		if(((CheckBox) findViewById(R.id.partyCheckBox)).isChecked())
			value += 2;
		if(((CheckBox) findViewById(R.id.gameCheckBox)).isChecked())
			value += 4;
		if(((CheckBox) findViewById(R.id.activityCheckBox)).isChecked())
			value += 8;
		if(((CheckBox) findViewById(R.id.artCheckBox)).isChecked())
			value += 16;
		if(((CheckBox) findViewById(R.id.otherCheckBox)).isChecked())
			value += 32;

		int distance = ((SeekBar) findViewById(R.id.distance_bar)).getProgress();
		int time = ((SeekBar) findViewById(R.id.clock_bar)).getProgress();
		Profile.submitPreferences(User.hash, value, distance, time, new Callback<Status>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Status obj) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				
			}
		});
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
}
