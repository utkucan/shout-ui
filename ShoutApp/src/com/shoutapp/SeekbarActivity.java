package com.shoutapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SeekbarActivity extends Activity {

	private SeekBar distanceControl, timeControl = null;
	private TextView distanceLabel, timeLabel = null;
	private Button filterDoneBtn = null;

	View parent2;

	public SeekbarActivity(View parent) {
		this.parent2 = parent;

		distanceControl = (SeekBar) parent.findViewById(R.id.distance_bar);
		int distance = getDistance(parent.getContext());
		distanceControl.setProgress(distance);

		distanceLabel = ((TextView) parent.findViewById(R.id.distanceLabel));
		distanceLabel.setText(distanceControl.getProgress() + "");

		distanceControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				progressChanged = seekBar.getProgress();
				if (progressChanged == 0) {

					distanceLabel.setText(progressChanged + " km");
				} else if (progressChanged == 1) {

					distanceLabel.setText(progressChanged + " km");
				} else {

					distanceLabel.setText(progressChanged + " kms");
				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

		});

		timeControl = (SeekBar) parent.findViewById(R.id.clock_bar);
		timeControl.setProgress(getTime(parent.getContext()));
		timeLabel = ((TextView) parent.findViewById(R.id.timeLabel));
		timeLabel.setText(timeControl.getProgress() + "");

		timeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				progressChanged = seekBar.getProgress();
				if (progressChanged == 0) {
					timeLabel.setText("NOW!");
				} else if (progressChanged == 1) {
					timeLabel.setText(progressChanged + " hour");
				} else {
					timeLabel.setText(progressChanged + " hours");
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		boolean[] checkList = getCheckBoxes(parent.getContext());

		((CheckBox) parent.findViewById(R.id.sportCheckBox)).setChecked(checkList[0]);
		((CheckBox) parent.findViewById(R.id.partyCheckBox)).setChecked(checkList[1]);
		((CheckBox) parent.findViewById(R.id.gameCheckBox)).setChecked(checkList[2]);
		((CheckBox) parent.findViewById(R.id.activityCheckBox)).setChecked(checkList[3]);
		((CheckBox) parent.findViewById(R.id.artCheckBox)).setChecked(checkList[4]);
		((CheckBox) parent.findViewById(R.id.otherCheckBox)).setChecked(checkList[5]);
		
		
		((Switch) parent.findViewById(R.id.silenceNotifications)).setChecked(getNotification(parent.getContext()));
	}

	public boolean[] getCheckBoxes(Context ctx) {
		SharedPreferences storedSettings = ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0);
		return new boolean[] { storedSettings.getBoolean("sports", true), storedSettings.getBoolean("party", true),
				storedSettings.getBoolean("game", true), storedSettings.getBoolean("activity", true), storedSettings.getBoolean("art", true),
				storedSettings.getBoolean("other", true) };
	}

	private int getDistance(Context ctx) {
//		SharedPreferences settings = ;
		return ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0).getInt("distance", 100);
	}
	private boolean getNotification(Context ctx){
		return ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0).getBoolean("notification", true);
	}
	private int getTime(Context ctx) {
		return ctx.getSharedPreferences(BaseActivity.FILTER_PREFS, 0).getInt("timeMin", 24);
	}

//	public BitmapDrawable writeOnDrawable(int drawableId, String text) {
//
//		Bitmap bm = BitmapFactory.decodeResource(parent2.getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
//
//		Paint paint = new Paint();
//		paint.setStyle(Style.FILL);
//		paint.setColor(Color.BLACK);
//		paint.setTextSize(20);
//
//		Canvas canvas = new Canvas(bm);
//		canvas.drawText(text, 0, bm.getHeight() / 2, paint);
//
//		return new BitmapDrawable(bm);
//	}
}
