package com.shoutapp;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	String TAG = "GcmIntentService";

	public GcmIntentService() {
		super("GcmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {

			if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)){

				sendNotification("Mesaj: " + extras.getString("mesaj"));
			}

			//            if (GoogleCloudMessaging.
			//                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			//                sendNotification("Send error: " + extras.toString());
			//            } else if (GoogleCloudMessaging.
			//                    MESSAGE_TYPE_DELETED.equals(messageType)) {
			//                sendNotification("Deleted messages on server: " +
			//                        extras.toString());
			//            // If it's a regular GCM message, do some work.
			//            } else if (GoogleCloudMessaging.
			//                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
			//                // This loop represents the service doing some work.
			////                for (int i=0; i<5; i++) {
			////                    Log.i(TAG, "Working... " + (i+1)
			////                            + "/5 @ " + SystemClock.elapsedRealtime());
			////                    try {
			////                        Thread.sleep(5000);
			////                    } catch (InterruptedException e) {
			////                    }
			////                }
			//                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
			//                // Post notification of received message.
			//                sendNotification("Mesaj: " + extras.getString("payload"));
			//                Log.i(TAG, "Received: " + extras.toString());
			//            }
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		//GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private static int notificationID = 0;
	private void sendNotification(String msg) {

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.icon)
		.setContentTitle("Yeni etkinlik!")
		.setContentText(msg);//"100 metre uzaðýnýzda tam size göre bir etkinlik var!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
						);
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);    	
		mNotificationManager.notify(notificationID++, mBuilder.build());
	}
}



//public void newNotification(Context ctx,String msg){
//	NotificationCompat.Builder mBuilder =
//	        new NotificationCompat.Builder(ctx)
//	        .setSmallIcon(R.drawable.icon)
//	        .setContentTitle("Yeni etkinlik!")
//	        .setContentText(msg);//"100 metre uzaðýnýzda tam size göre bir etkinlik var!");
//	// Creates an explicit intent for an Activity in your app
//	Intent resultIntent = new Intent(ctx, BaseActivity.class);
//
////	// The stack builder object will contain an artificial back stack for the
////	// started Activity.
////	// This ensures that navigating backward from the Activity leads out of
////	// your application to the Home screen.
////	TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
////	// Adds the back stack for the Intent (but not the Intent itself)
////	stackBuilder.addParentStack(MainActivity.class);
////	// Adds the Intent that starts the Activity to the top of the stack
////	stackBuilder.addNextIntent(resultIntent);
////	PendingIntent resultPendingIntent =
////	        stackBuilder.getPendingIntent(
////	            0,
////	            PendingIntent.FLAG_UPDATE_CURRENT
////	        );
////	mBuilder.setContentIntent(resultPendingIntent);
//	NotificationManager mNotificationManager =
//	    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//	// mId allows you to update the notification later on.
//	mNotificationManager.notify(99, mBuilder.build());
//	
//}
