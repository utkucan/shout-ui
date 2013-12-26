package com.shoutapp;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	//private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	String TAG = "GcmIntentService";

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private static int notificationID = 0;

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {

			if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
				String type = extras.getString("type");
				int relatedid = Integer.parseInt(extras.getString("relatedid","0"));
				long time = Long.parseLong(extras.getString("time","1321354"));
				String message = extras.getString("message","Boþ mesaj");
				Log.d("Cloud Message", type + "," + relatedid + "," + time + "," + message);			
				sendNotification("Mesaj: " +message);
			}
		}
        GcmBroadcastReceiver.completeWakefulIntent(intent);		
        Log.d("Shout", "WakeLock released...");
        
	}

	private void sendNotification(String msg) {
//		this.getSharedPreferences(BaseActivity.FILTER_PREFS, 0).getBoolean("notification", true);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.icon).setContentTitle("Yeni etkinlik!").setContentText(msg);// "100 metre uzaðýnýzda tam size göre bir etkinlik var!");
		// Creates an explicit intent for an Activity in your app
		
		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		
		
		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(notificationID++, mBuilder.build());
	}
}
