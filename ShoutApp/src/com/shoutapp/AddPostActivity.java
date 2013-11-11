package com.shoutapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class AddPostActivity extends BaseActivity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
		
		
		View add_post = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_post, null);
		RelativeLayout addPostLayout = (RelativeLayout)add_post.findViewById(R.id.add_post_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			      RelativeLayout.LayoutParams.WRAP_CONTENT,
			      RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		addPostLayout.setLayoutParams(lp);
        mainLayout.addView(add_post);

	}
}
