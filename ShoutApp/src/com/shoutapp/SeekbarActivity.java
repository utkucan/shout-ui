package com.shoutapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SeekbarActivity extends Activity{

	private SeekBar distanceControl,timeControl = null;
	private TextView distanceLabel , timeLabel = null;
	private Button filterDoneBtn = null;
	View parent2;
	public SeekbarActivity(View parent){
		this.parent2 = parent;


		distanceControl = (SeekBar) parent.findViewById(R.id.distance_bar);
		//volumeControl.setThumb(writeOnDrawable(R.drawable.add_event_button, volumeControl.getProgress()+""));
		distanceLabel = ((TextView) parent.findViewById(R.id.distanceLabel));
		distanceLabel.setText(distanceControl.getProgress()+"");

		distanceControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

				progressChanged = seekBar.getProgress();
				if(progressChanged == 0 ){
					//Toast.makeText(parent2.getContext(), "Near Shouts will be shown!",Toast.LENGTH_SHORT).show();
					distanceLabel.setText(progressChanged+"km"); 
				}
				else if( progressChanged == 1){
					//Toast.makeText(parent2.getContext(), "Shouts within "+ progressChanged +"km will be shown!",Toast.LENGTH_SHORT).show();
					distanceLabel.setText(progressChanged+"km"); 
				}
				else{
					//Toast.makeText(parent2.getContext(), "Shouts within "+ progressChanged +"kms will be shown!",Toast.LENGTH_SHORT).show();
					distanceLabel.setText(progressChanged+"kms"); 
				}

				//volumeControl.setThumb(writeOnDrawable(R.drawable.add_event_button, progressChanged+""));
			}


			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
			}

		});


		timeControl = (SeekBar)  parent.findViewById(R.id.clock_bar);
		timeLabel = ((TextView) parent.findViewById(R.id.timeLabel));
		timeLabel.setText(timeControl.getProgress()+"");


		timeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

				progressChanged = seekBar.getProgress();
				if(progressChanged == 0 ){
					//Toast.makeText(parent2.getContext(), "Currently active Shouts will appear",Toast.LENGTH_SHORT).show();
					timeLabel.setText("NOW!");}
				else if( progressChanged == 1){
					//Toast.makeText(parent2.getContext(), "Shouts within "+ progressChanged +"hour will be shown!",Toast.LENGTH_SHORT).show();
					timeLabel.setText(progressChanged+" hour");}
				else{
					//Toast.makeText(parent2.getContext(), "Shouts within "+ progressChanged +"hours will be shown!",Toast.LENGTH_SHORT).show();
					timeLabel.setText(progressChanged+" hours");}
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {

				//timeLabel.setText(progressChanged+"");
				//timeControl.setThumb(writeOnDrawable(R.drawable.add_event_button, progressChanged+""));
			}
		});


	}
	public BitmapDrawable writeOnDrawable(int drawableId, String text){

		Bitmap bm = BitmapFactory.decodeResource(parent2.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);

		Paint paint = new Paint(); 
		paint.setStyle(Style.FILL);  
		paint.setColor(Color.BLACK); 
		paint.setTextSize(20); 

		Canvas canvas = new Canvas(bm);
		canvas.drawText(text, 0, bm.getHeight()/2, paint);

		return new BitmapDrawable(bm);
	}




}



// 
//public class SeekbarActivity extends Activity {
//
//	private SeekBar volumeControl,timeControl = null;
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_seekbar);
//
//		volumeControl = (SeekBar) findViewById(R.id.distance_bar);
//
//		volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			int progressChanged = 0;
//
//			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
//
//				progressChanged = seekBar.getProgress();
//			}
//
//			public void onStartTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//			}
//
//			public void onStopTrackingTouch(SeekBar seekBar) {
//				if(progressChanged == 0 )
//					Toast.makeText(SeekbarActivity.this, "Near Shouts will be shown!", 
//						Toast.LENGTH_SHORT).show();
//				else if( progressChanged == 1)
//					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"km will be shown!", 
//						Toast.LENGTH_SHORT).show();
//				else
//					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"kms will be shown!", 
//							Toast.LENGTH_SHORT).show();
//			}
//		});
//		
//		
//		timeControl = (SeekBar) findViewById(R.id.clock_bar);
//
//		timeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
//			int progressChanged = 0;
//
//			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
//				
//				progressChanged = seekBar.getProgress();
//			}
//
//			public void onStartTrackingTouch(SeekBar seekBar) {
//				// TODO Auto-generated method stub
//			}
//
//			public void onStopTrackingTouch(SeekBar seekBar) {
//				if(progressChanged == 0 )
//					Toast.makeText(SeekbarActivity.this, "Currently active Shouts will appear", 
//						Toast.LENGTH_SHORT).show();
//				else if( progressChanged == 1)
//					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"hour will be shown!", 
//						Toast.LENGTH_SHORT).show();
//				else
//					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"hours will be shown!", 
//							Toast.LENGTH_SHORT).show();
//			}
//		});
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.layout.activity_seekbar, menu);
//		return true;
//	}
//
//}