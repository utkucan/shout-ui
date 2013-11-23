package com.shoutapp;

import java.util.Calendar;
import java.util.Date;

import com.facebook.FacebookRequestError.Category;
import com.facebook.Session.NewPermissionsRequest;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;


import java.lang.reflect.Field;


public class AddPostActivity extends BaseActivity {

	EditText saat, duration,title, description;
	Context appContext;
	ScrollView scrollv;
	RelativeLayout mapLay, save_btn_lay, cancel_btn_lay;
	NoDefaultSpinner categoryPicker;
	ImageButton save_btn,cancel_btn;
	boolean isMapLayHeightSet = false;
	boolean isActivityStarted = false;
	
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
        duration = (EditText)findViewById(R.id.duration);
        mapLay = (RelativeLayout)findViewById(R.id.mapLay);
        scrollv = (ScrollView)findViewById(R.id.addPostScrollView);
        save_btn_lay = (RelativeLayout)findViewById(R.id.save_btn_holder);
        save_btn = (ImageButton)findViewById(R.id.save_btn);
        categoryPicker = (NoDefaultSpinner)findViewById(R.id.categoryList);
        title = (EditText)findViewById(R.id.header);
        description = (EditText)findViewById(R.id.description);
        cancel_btn_lay = (RelativeLayout)findViewById(R.id.cancel_btn_holder);
        cancel_btn = (ImageButton)findViewById(R.id.cancel_btn);
        
        saat.setInputType(InputType.TYPE_NULL);
        saat.setOnFocusChangeListener(focusChanged);
        saat.setOnClickListener(onClicked);
        
        title.setOnFocusChangeListener(focusChanged);
        description.setOnFocusChangeListener(focusChanged);
//        title.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_NEXT);
//        description.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        
        
        duration.setInputType(InputType.TYPE_NULL);
        duration.setOnFocusChangeListener(focusChanged);
        duration.setOnClickListener(onClicked);
        
        ViewTreeObserver vto = scrollv.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
            	if(!isMapLayHeightSet){
	            	int h = scrollv.getMeasuredHeight();
	            	mapLay.getLayoutParams().height = (2*h)/5;
	            	isMapLayHeightSet = true;
	            	scrollv.scrollTo(0, 0);
            	}
                return true;
            }
        });
        
        save_btn_lay.setOnClickListener(onClicked);
        save_btn.setOnClickListener(onClicked);
        cancel_btn.setOnClickListener(onClicked);
        cancel_btn_lay.setOnClickListener(onClicked);
	}
	
	private OnClickListener onClicked = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.equals(save_btn_lay) || v.equals(save_btn)){
				if(categoryPicker.getSelectedItemPosition() == -1){
					Toast.makeText(appContext, "Please select a category for your post.", Toast.LENGTH_LONG).show();
					return;
				}
				String time = saat.getText().toString();
				if(!time.contains(":")){
					Toast.makeText(appContext, "Please specify the start time.", Toast.LENGTH_LONG).show();
					return;
				}
				String sure = duration.getText().toString();
				if(!sure.contains(":")){
					Toast.makeText(appContext, "Please specify the duration.", Toast.LENGTH_LONG).show();
					return;
				}
				
				String header = title.getText().toString();
				if(header.equals("Title") || header.equals("")){
					Toast.makeText(appContext, "Please specify the Title of post.", Toast.LENGTH_LONG).show();
					return;
				}
				
				String desc = description.getText().toString();
				if(desc.equals("Description") || desc.equals("")){
					Toast.makeText(appContext, "Please specify the Description of post.", Toast.LENGTH_LONG).show();
					return;
				}			
				String category = (String)categoryPicker.getSelectedItem();
				if(!Model.add_post("",category, time, sure, header, desc)){
					Toast.makeText(appContext, "Error!", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(appContext, "Your Post is successfully added!", Toast.LENGTH_LONG).show();
					onBackPressed();
				}
			}else if(v.equals(saat)){
				openTimeDialog();
			}else if(v.equals(duration)){
				openDurationDialog();
			}else if(v.equals(cancel_btn_lay) || v.equals(cancel_btn)){
				onBackPressed();
			}
		}
	};
	
	private OnFocusChangeListener focusChanged = new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(isActivityStarted){
				if(hasFocus){
					if(v.equals(duration)){
						openDurationDialog();
					}
					else if(v.equals(saat)){
						openTimeDialog();
					}
					else if(v.equals(title) && title.getText().toString().equals("Title")){
						title.setText("");
					}
					else if(v.equals(description) && description.getText().toString().equals("Description")){
						description.setText("");
					}
				}else{
					if(v.equals(title) && title.getText().toString().equals("")){
						title.setText("Title");
					}
					else if(v.equals(description) && description.getText().toString().equals("")){
						description.setText("Description");
					}
				}
			}else{
				isActivityStarted =true;
			}
		}
	};
	
	private void openDurationDialog(){
		String sure = duration.getText().toString();
		int hour = 0;
		int min = 0;
		if(sure.contains(":")){
			String[] comp = sure.split(":");
			hour = Integer.parseInt(comp[0]);
			min = Integer.parseInt(comp[1]);
		}
		
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
