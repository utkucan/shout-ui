package com.shoutapp;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BaseActivity extends SlidingMenuBaseActivity{

	ImageButton slidingMenuBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
		setSlidingActionBarEnabled(true);
		setContentView(R.layout.main_frame);
		
		slidingMenuBtn = (ImageButton)findViewById(R.id.slidingMenuButton);
		slidingMenuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SlidingMenu sm = getSlidingMenu();
				sm.toggle(true);
			}
		});
	}
}
