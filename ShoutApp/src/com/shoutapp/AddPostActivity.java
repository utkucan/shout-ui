package com.shoutapp;

import java.util.Calendar;
import java.util.Date;

import com.facebook.Session.NewPermissionsRequest;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;


import java.lang.reflect.Field;


public class AddPostActivity extends BaseActivity {

	EditText saat;
	EditText duration;
	Context appContext;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
		appContext = this;
		
		View add_post = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_post, null);
		RelativeLayout addPostLayout = (RelativeLayout)add_post.findViewById(R.id.add_post_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
			      RelativeLayout.LayoutParams.WRAP_CONTENT,
			      RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		addPostLayout.setLayoutParams(lp);
        mainLayout.addView(add_post);
        
        
        saat = (EditText)findViewById(R.id.time);
        saat.setInputType(InputType.TYPE_NULL);
        saat.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				if(v.equals(saat) && hasFocus){
					openTimeDialog();
				}
			}
		});
        saat.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openTimeDialog();
			}
		});
        
//        NoDefaultSpinner category = (NoDefaultSpinner)findViewById(R.id.categoryList);
//        category.requestFocus();
        
        duration = (EditText)findViewById(R.id.duration);
        duration.setInputType(InputType.TYPE_NULL);
        
        duration.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				if(v.equals(duration) && hasFocus){
					openDurationDialog();
				}
			}
		});
        duration.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDurationDialog();
			}
		});
        
//        EditText description = (EditText)findViewById(R.id.description);
//        description.clearFocus();

	}
	
	private void openDurationDialog(){
		String sure = duration.getText().toString();
		int hour = 0;
		int min = 0;
		if(sure.contains(":")){
			String[] comp = sure.split(":");
			hour = Integer.parseInt(comp[0]);
			min = Integer.parseInt(comp[1]);
		}
		
//		final TimePickerDialog d = new TimePickerDialog(appContext, new OnTimeSetListener() {
//			
//			@Override
//			public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
//				// TODO Auto-generated method stub
//				StringBuilder sb = new StringBuilder();
//				if(selectedHour<10)
//					sb.append("0");
//				sb.append(selectedHour);
//				sb.append(':');
//				if(selectedMinute<10)
//					sb.append("0");
//				sb.append(selectedMinute);
//				duration.setText(sb);
//				
//				StringBuilder text = new StringBuilder();
//				text.append("The duration is ");
//				int dur = selectedHour*60 + selectedMinute;
//				if(dur==1){
//					text.append(dur + " minute");
//				}else{
//					text.append(dur + " minutes");
//				}
//				Toast toast = Toast.makeText(appContext, text, Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
//				
//				((TextView)((LinearLayout)toast.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
//				toast.show();
//			}
//		}, hour, min,true);
//		try {
//			TimePicker mTimePicker = (TimePicker)(d.getClass().getSuperclass().getDeclaredField("mTimePicker").get(d));
//			mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
//				
//				@Override
//				public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//					// TODO Auto-generated method stub
//					int dur = hourOfDay*60 + minute;
//					if(dur>240){
//						d.updateTime(4, 0);
//					}
//				}
//			});
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchFieldException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		TimePickerDialog d = new RangeTimePickerDialog(appContext, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				if(selectedHour<10)
					sb.append("0");
				sb.append(selectedHour);
				sb.append(':');
				if(selectedMinute<10)
					sb.append("0");
				sb.append(selectedMinute);
				duration.setText(sb);
				
				StringBuilder text = new StringBuilder();
				text.append("The duration is ");
				int dur = selectedHour*60 + selectedMinute;
				if(dur==1){
					text.append(dur + " minute");
				}else{
					text.append(dur + " minutes");
				}
				Toast toast = Toast.makeText(appContext, text, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
				
				((TextView)((LinearLayout)toast.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
				toast.show();
			}
		},hour, min,true);
//		d.updateTime(2, 50);
		d.show();
	}
	
	private void openTimeDialog(){
		String time = saat.getText().toString();
		int hour = 0;
		int min = 0;
		if(time.contains(":")){
			String[] comp = time.split(":");
			hour = Integer.parseInt(comp[0]);
			min = Integer.parseInt(comp[1]);
		}else{
			Calendar c = Calendar.getInstance(); 
			hour = c.get(Calendar.HOUR_OF_DAY);
			min = c.get(Calendar.MINUTE);
		}
		Dialog d = (Dialog)(new TimePickerDialog(appContext, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				if(selectedHour<10)
					sb.append("0");
				sb.append(selectedHour);
				sb.append(':');
				if(selectedMinute<10)
					sb.append("0");
				sb.append(selectedMinute);
				saat.setText(sb);
				
				Calendar c = Calendar.getInstance(); 
				int diff = (selectedHour*60+selectedMinute) - (c.get(Calendar.HOUR_OF_DAY)*60 + c.get(Calendar.MINUTE)) ;
				if(diff<=0){
					diff += (24*60)-1;
				}
				int hour = diff / 60;
				int min = diff % 60;
				StringBuilder text = new StringBuilder();
				text.append("The remaining time is\n");
				if(hour>0){
					if(hour == 1)
						text.append(hour + " hour ");
					else
						text.append(hour + " hours ");
				}
				if(min>0){
					if(min == 1)
						text.append(min + " minute");
					else
						text.append(min + " minutes");
				}
				Toast toast = Toast.makeText(appContext, text, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
				
				((TextView)((LinearLayout)toast.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
				toast.show();
			}
		}, hour, min,true));
		d.show();
	}

	private class RangeTimePickerDialog extends TimePickerDialog{
		private int currentHour = 0;
		private int currentMinute = 0;
		private int maxDuration = 240;
		private boolean frst = false; 
		public RangeTimePickerDialog(Context context,
				OnTimeSetListener callBack, int hourOfDay, int minute,
				boolean is24HourView) {
			super(context, callBack, hourOfDay, minute, is24HourView);
			currentHour = hourOfDay;
			currentMinute = minute;
		}
		
//		public RangeTimePickerDialog(Context context,
//				OnTimeSetListener callBack) {
//			super(context, callBack, 0, 0, true);
//		}

		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			super.onTimeChanged(view, hourOfDay, minute);
			if(hourOfDay*60 + minute<=maxDuration){
				currentHour = hourOfDay;
				currentMinute = minute;
				frst=false;
			}
			else{
				if(!frst){
					frst = true;
					updateTime(currentHour, currentMinute);
				}
			}
		}
	}
}
