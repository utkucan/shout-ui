package com.shoutapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SeekbarActivity extends Activity {

	private SeekBar volumeControl,timeControl = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seekbar);

		volumeControl = (SeekBar) findViewById(R.id.distance_bar);

		volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){

				progressChanged = seekBar.getProgress();
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				if(progressChanged == 0 )
					Toast.makeText(SeekbarActivity.this, "Near Shouts will be shown!", 
						Toast.LENGTH_SHORT).show();
				else if( progressChanged == 1)
					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"km will be shown!", 
						Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"kms will be shown!", 
							Toast.LENGTH_SHORT).show();
			}
		});
		
		
		timeControl = (SeekBar) findViewById(R.id.clock_bar);

		timeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				
				progressChanged = seekBar.getProgress();
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				if(progressChanged == 0 )
					Toast.makeText(SeekbarActivity.this, "Currently active Shouts will appear", 
						Toast.LENGTH_SHORT).show();
				else if( progressChanged == 1)
					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"hour will be shown!", 
						Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(SeekbarActivity.this, "Shouts within "+ progressChanged +"hours will be shown!", 
							Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.activity_seekbar, menu);
		return true;
	}

}