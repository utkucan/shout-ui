package com.shoutapp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.shoutapp.entity.FetchJsonTask.Callback;
import com.shoutapp.entity.Login;

public class LoginActivity extends FragmentActivity {

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
				sendRegistrationIdToBackend();
				storeRegistrationId(context, regid);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}

		private void sendRegistrationIdToBackend() {
			Person user = mPlusClient.getCurrentPerson();
			if (user != null) {
				String id = user.getId();
				Login.peform(id, regid, new Callback<Login>() {
					@Override
					public void onFail() {

					}

					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(Login login) {
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						User.hash = login.getHash();
						User.user_id = login.getUserId();
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});

				Model.userName = user.getDisplayName();
				Model.profile_pic_url = user.getImage().getUrl();
			} else {
				mPlusClient.disconnect();
			}

		}
	}
	private static final String TAG = "Login";
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;

	private Activity login_activity;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	// googlecloudmessaging
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

	private View.OnClickListener gp_onClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.gp_login_btn) {

				if (!mPlusClient.isConnected()) {
					if (mConnectionResult != null) {
						try {
							mConnectionResult.startResolutionForResult(login_activity, REQUEST_CODE_RESOLVE_ERR);
						} catch (SendIntentException e) {
							mConnectionResult = null;
							mPlusClient.connect();
						}
						mConnectionProgressDialog.dismiss();
					} else {
						mPlusClient.connect();
					}
				} else {
					mPlusClient.disconnect();
				}
			}
		}
	};

	private ConnectionCallbacks gp_connectionCallback = new ConnectionCallbacks() {

		@Override
		public void onConnected(Bundle connectionHint) {
			// Log.d("connected", "connected");
			checkPlayServices();
			registerInBackground();
		}

		@Override
		public void onDisconnected() {

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

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("GCMDemo", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(LoginActivity.class.getSimpleName(), Context.MODE_PRIVATE);
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
		if (registrationId == null || registrationId == "") {// .isEmpty()) {
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
				Toast.makeText(getApplicationContext(), msg + "\n", 100);
			}
		}.execute(null, null, null);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		context = this;
		checkPlayServices();
		if (getRegistrationId(this).equals("")) {
			registerInBackground();
		}

		mPlusClient = new PlusClient.Builder(this, gp_connectionCallback, gp_OnConnectionFailedListener).setVisibleActivities(
				"http://schemas.google.com/AddActivity", "http://schemas.google.com/ListenActivity").build();
		login_activity = this;
		if (mPlusClient.isConnected()) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other
																// Activities
																// from stack
			startActivity(intent);
		}
		setContentView(R.layout.login);
		SignInButton gp_login = (SignInButton) findViewById(R.id.gp_login_btn);
		for (int i = 0; i < gp_login.getChildCount(); i++) {
			View v = gp_login.getChildAt(i);
			if (v instanceof TextView) {
				TextView tv = (TextView) v;
				tv.setText("Log in with Google");
			}
		}
		((SignInButton) findViewById(R.id.gp_login_btn)).setOnClickListener(gp_onClick);
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");

		onClickX();
	}
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {
		new RegistrationClass().execute(null, null, null);
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

}
