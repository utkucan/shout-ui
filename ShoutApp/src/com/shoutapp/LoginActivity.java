package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity{

	private static final String TAG = "Login";
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;
	private Activity login_activity;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
	
	//googlecloudmessaging
	GoogleCloudMessaging gcm;
	String regid;
	Context context;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	String SENDER_ID = "828218329595";

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("GCMDemo", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId == null || registrationId == ""){//.isEmpty()) {
			Log.i("GCMDemo", "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("GCMDemo", "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(LoginActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i("GCMDemo", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new RegistrationClass().execute(null,null,null);
	}
	// GCM END
	 public void onClickX() {

	       
	            new AsyncTask<Void, Void, String>() {
	                @Override
	                protected String doInBackground(Void... params) {
	                    String msg = "";
	                    try {
	                        Bundle data = new Bundle();
	                        data.putString("my_message", "Hello World");
	                        data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
	                        String id = Integer.toString(msgId.incrementAndGet());
	                        if (gcm == null) {
	        					gcm = GoogleCloudMessaging.getInstance(context);
	        				}
	                        gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
	                        msg = "Sent message";
	                    } catch (IOException ex) {
	                        msg = "Error :" + ex.getMessage();
	                    }
	                    return msg;
	                }

	                @Override
	                protected void onPostExecute(String msg) {
	                    Toast.makeText(getApplicationContext(),msg + "\n",100);
	                }
	            }.execute(null, null, null);
	       
	    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
				
		context = this;
		checkPlayServices();
		if(getRegistrationId(this).equals("")){
			registerInBackground();
		}		
		
		mPlusClient = 
				new PlusClient.Builder(this, gp_connectionCallback, gp_OnConnectionFailedListener)
		.setVisibleActivities("http://schemas.google.com/AddActivity","http://schemas.google.com/ListenActivity")
		.build();
		login_activity = this;
		if(mPlusClient.isConnected()){
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
			startActivity(intent);
		}
		setContentView(R.layout.login);
		SignInButton gp_login = (SignInButton)findViewById(R.id.gp_login_btn);
		for (int i = 0; i < gp_login.getChildCount(); i++) {
			View v = gp_login.getChildAt(i);
			if (v instanceof TextView) {
				TextView tv = (TextView) v;
				tv.setText("Log in with Google");
			}
		}
		((SignInButton)findViewById(R.id.gp_login_btn)).setOnClickListener(gp_onClick);
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
		
		onClickX();
	}

	protected void onStart(){
		super.onStart();
	}

	protected void onStop(){
		super.onStop();
	}
	protected void onResume() {
		super.onResume(); 
		 
//		registerInBackground();
	}
	private View.OnClickListener gp_onClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.gp_login_btn){

				if(!mPlusClient.isConnected()){
					if(mConnectionResult != null){
						try{
							mConnectionResult.startResolutionForResult(login_activity, REQUEST_CODE_RESOLVE_ERR);
						}catch(SendIntentException e){
							mConnectionResult = null;
							mPlusClient.connect();
						}
						mConnectionProgressDialog.dismiss();
					}else{
						mPlusClient.connect();
					}
				}else{
					mPlusClient.disconnect();
				}
			}
		}
	};


	private ConnectionCallbacks gp_connectionCallback = new ConnectionCallbacks() {

		@Override
		public void onDisconnected() {

		}

		@Override
		public void onConnected(Bundle connectionHint) {
			Log.d("connected","connected");
			checkPlayServices();
			//if(getRegistrationId(LoginActivity.this).equals("")){
				registerInBackground();
			//}		
			
			
		}
	};
	private OnConnectionFailedListener gp_OnConnectionFailedListener = new OnConnectionFailedListener() {

		@Override
		public void onConnectionFailed(ConnectionResult result) {
			if (mConnectionProgressDialog.isShowing()) {
				if (result.hasResolution()) {
					try {
						result.startResolutionForResult(login_activity, REQUEST_CODE_RESOLVE_ERR);
					} catch (SendIntentException e) {
						mPlusClient.connect();
					} 
				}
			}
			mConnectionResult = result;
		}
	};
	private class RegistrationClass extends AsyncTask<Void, Void, String> {


		@Override
		protected String doInBackground(Void... params) {
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				msg = "Device registered, registration ID=" + regid;

				// You should send the registration ID to your server over HTTP,
				// so it can use GCM/HTTP or CCS to send messages to your app.
				// The request to your server should be authenticated if your app
				// is using accounts.
				sendRegistrationIdToBackend();

				// For this demo: we don't need to send it because the device
				// will send upstream messages to a server that echo back the
				// message using the 'from' address in the message.

				// Persist the regID - no need to register again.
				storeRegistrationId(context, regid);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
				// If there is an error, don't just keep trying to register.
				// Require the user to click a button again, or perform
				// exponential back-off.
			}
			return msg;
		}

		@Override
		protected void onPostExecute(String msg) {

		}


		/**
		 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
		 * or CCS to send messages to your app. Not needed for this demo since the
		 * device sends upstream messages to a server that echoes back the message
		 * using the 'from' address in the message.
		 */

		private void sendRegistrationIdToBackend() {
			// Your implementation here.

			// register olan makinanýn kodu neymiþ onu bi görek
			Person user = mPlusClient.getCurrentPerson();
			if(user != null){
				String id = user.getId();
				Log.d("reg_id", "msg"+ regid);
				Register r = new Register(0, id, regid, new RespCallback() {

					@Override
					public void callback_events(ArrayList<Event> Events) {}

					@Override
					public void callback_ack() {
						// TODO Auto-generated method stub
						Log.d("callback_ack:",User.hash);
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
				r.execute();

				Model.userName = user.getDisplayName();
				Model.profile_pic_url =user.getImage().getUrl();
			}else{
				mPlusClient.disconnect();
			}


		}
	}

}
