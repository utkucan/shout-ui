package com.shoutapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

public class AddPostActivity extends BaseActivity {

	EditText saat;
	
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
        saat = (EditText)findViewById(R.id.time);
        saat.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog d = (Dialog)(new TimePickerDialog(getApplicationContext(), timePickerListener, 12, 10,false));
				d.show();
			}
		});
//        TimePickerDialog

	}
	
	private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			StringBuilder sb = new StringBuilder();
			sb.append(selectedHour);
			sb.append(':');
			sb.append(selectedMinute);
			saat.setText(sb);

		}

	};

}
