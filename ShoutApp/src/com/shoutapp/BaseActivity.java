package com.shoutapp;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BaseActivity extends SlidingMenuBaseActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

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
				Intent i = new Intent();
		        i.setClassName("com.shoutapp", "com.shoutapp.SeekbarActivity");
		        startActivity(i);
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
}
