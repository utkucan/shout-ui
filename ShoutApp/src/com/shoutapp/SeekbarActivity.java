package com.shoutapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SeekbarActivity extends Activity{

	private SeekBar distanceControl,timeControl = null;
	private TextView distanceLabel , timeLabel = null;
	private Button filterDoneBtn = null;

	 
	View parent2;
	public SeekbarActivity(View parent){
		this.parent2 = parent;
		//newNotification(parent.getContext());
		
		distanceControl = (SeekBar) parent.findViewById(R.id.distance_bar);
		int distance = getDistance(parent.getContext());
		distanceControl.setProgress(distance);
		
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
		timeControl.setProgress(getTime(parent.getContext()));
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
		
		boolean[] checkList = getCheckBoxes(parent.getContext());
		

		((CheckBox) parent.findViewById(R.id.sportCheckBox)).setChecked(checkList[0]);
		((CheckBox) parent.findViewById(R.id.partyCheckBox)).setChecked(checkList[1]);
		((CheckBox) parent.findViewById(R.id.gameCheckBox)).setChecked(checkList[2]);
		((CheckBox) parent.findViewById(R.id.activityCheckBox)).setChecked(checkList[3]);
		((CheckBox) parent.findViewById(R.id.artCheckBox)).setChecked(checkList[4]);
		((CheckBox) parent.findViewById(R.id.otherCheckBox)).setChecked(checkList[5]);
		
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
	public void newNotification(Context ctx){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(ctx)
		        .setSmallIcon(R.drawable.icon)
		        .setContentTitle("Yeni etkinlik!")
		        .setContentText("100 metre uzaðýnýzda tam size göre bir etkinlik var!");
/*		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(ctx, BaseActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		        mBuilder.setContentIntent(resultPendingIntent);
		        */
		
		NotificationManager mNotificationManager =
		    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(99, mBuilder.build());
		
	}
	public int getDistance(Context ctx){
		SharedPreferences settings = ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0);
		int silent = settings.getInt("distance",100);
		return silent;
	}
	public int getTime(Context ctx){
		SharedPreferences settings = ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 1);
		int silent = settings.getInt("timeMin",24);
		return silent;
	}
	public boolean[] getCheckBoxes(Context ctx){
		SharedPreferences storedSettings = ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0);
	return new boolean[]{
			storedSettings.getBoolean("sports",true),
			storedSettings.getBoolean("party",true),
			storedSettings.getBoolean("game",true),
			storedSettings.getBoolean("activity",true),
			storedSettings.getBoolean("art",true),
			storedSettings.getBoolean("other",true)
			};
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