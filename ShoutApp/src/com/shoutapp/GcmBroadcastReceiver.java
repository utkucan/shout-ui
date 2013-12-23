package com.shoutapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

//import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(!context.getSharedPreferences(BaseActivity.FILTER_PREFS, 0).getBoolean("notification", true)){
			Log.d("BTOM-PUSH","notification kapalý");
			return;			
		}else{
			Log.d("BTOM-PUSH","notification açýk");			
		}
		
		ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
	}
}
