package com.shoutapp;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
}
