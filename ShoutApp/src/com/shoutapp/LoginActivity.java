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
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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

public class LoginActivity extends FragmentActivity implements ConnectionCallbacks, OnConnectionFailedListener, View.OnClickListener {

	private class RegistrationClass extends AsyncTask<Void, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(context);
			pd.setTitle("Loading..");
			pd.setMessage("Contacing ShoutApp cloud..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();
			Log.d("Register in back", "started");
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.d("Register in back", "goin");

			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regid = gcm.register(SENDER_ID);
				msg = "Device registered, registration ID=" + regid;
				sendRegistrationIdToBackend();
				storeRegistrationId(context, regid);
				Log.d("Register in back", "stored");
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
			}
			return msg;
		}

		private void sendRegistrationIdToBackend() {
			Log.d("Register in back", "send reg to backend");
			Person user = mPlusClient.getCurrentPerson();

			if (user != null) {
				// String id = user.getId();
				Login.peform(mPlusClient.getCurrentPerson().getId(), regid, new Callback<Login>() {
					@Override
					public void onFail() {
						Log.d("Shout-registration", "sýkýntý oldu gibi");
						if (pd != null) {
							pd.dismiss();
						}
						if (mConnectionProgressDialog.isShowing())
							mConnectionProgressDialog.dismiss();
					}

					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(Login login) {

						Log.d("Shout-registration", "oldu gibi");
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						User.hash = login.getHash();
						User.user_id = login.getUserId();

						SharedPreferences settings = getSharedPreferences(SAVEHASH, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("hashval", User.hash);
						editor.putInt("userid", User.user_id);
						editor.commit();

						if (pd != null) {
							pd.dismiss();
						}
						if (mConnectionProgressDialog.isShowing())
							mConnectionProgressDialog.dismiss();
						// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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

	public static final String SAVEHASH = "SAVEHASH";
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

		SharedPreferences sp = getSharedPreferences(LoginActivity.SAVEHASH, 0);
		User.hash = sp.getString("hashval", null);

		if (User.hash != null) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {

			mPlusClient = new PlusClient.Builder(this, this, this).setVisibleActivities("http://schemas.google.com/AddActivity",
					"http://schemas.google.com/ListenActivity").build();

			Log.e("Shout!-REGID", getRegistrationId(this));
			context = this;
			checkPlayServices();
			login_activity = this;

			setContentView(R.layout.login);
			SignInButton gp_login = (SignInButton) findViewById(R.id.gp_login_btn);
			gp_login.setOnClickListener(this);

			for (int i = 0; i < gp_login.getChildCount(); i++) {
				View v = gp_login.getChildAt(i);
				if (v instanceof TextView) {
					TextView tv = (TextView) v;
					tv.setText("Log in with Google");
				}
			}
			mConnectionProgressDialog = new ProgressDialog(this);
			mConnectionProgressDialog.setMessage("Signing in...");
		}
	}

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

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.d("fail", "noluo amk");
		if (mConnectionProgressDialog.isShowing()) {
			Log.d("fail", "if icinde");
			if (result.hasResolution()) {
				Log.d("fail", "bi daha if icinde");
				try {
					result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
					Log.d("fail", "startaa resolution after");
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		String accountName = mPlusClient.getAccountName();
		mConnectionProgressDialog.dismiss();
		// Toast.makeText(this, accountName + " is connected.",
		// Toast.LENGTH_LONG).show();
		registerInBackground();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.gp_login_btn && !mPlusClient.isConnected()) {
			if (mConnectionResult == null) {
				Log.d("aa", "dialog show");
				mConnectionProgressDialog.show();
				mPlusClient.connect();
			} else {
				Log.d("aa", "start reoslution 1");
				try {
					mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					// Try connecting again.
					mConnectionResult = null;
					mPlusClient.connect();
				}
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
//		mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//mPlusClient.disconnect();
	}
}
