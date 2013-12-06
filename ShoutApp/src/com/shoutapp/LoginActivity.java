package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends FragmentActivity{
	
	private static final String TAG = "Login";
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private ProgressDialog mConnectionProgressDialog;
	private Activity login_activity;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPlusClient = new PlusClient.Builder(this, gp_connectionCallback, gp_OnConnectionFailedListener).setVisibleActivities("http://schemas.google.com/AddActivity","http://schemas.google.com/ListenActivity").build();
		login_activity = this;
		if(mPlusClient.isConnected()){
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
//			intent.putExtra("loginType", "g");
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
	}
	
	protected void onStart(){
		super.onStart();
//		mPlusClient.connect();
	}
	
	protected void onStop(){
		super.onStop();
//		mPlusClient.disconnect();
	}
	
	private View.OnClickListener gp_onClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.gp_login_btn /*&& !mPlusClient.isConnected() && mConnectionResult != null*/){
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
		
//		private Bitmap getImageBitmap(String url) {
//	        Bitmap bm = null;
//	        try {
//	            URL aURL = new URL(url);
//	            URLConnection conn = aURL.openConnection();
//	            conn.connect();
//	            InputStream is = conn.getInputStream();
//	            BufferedInputStream bis = new BufferedInputStream(is);
//	            bm = BitmapFactory.decodeStream(bis);
//	            bis.close();
//	            is.close();
//	       } catch (IOException e) {
//	           Log.e(TAG, "Error getting bitmap", e);
//	       }
//	       return bm;
//	    }
		
		@Override
		public void onConnected(Bundle connectionHint) {
			Person user = mPlusClient.getCurrentPerson();
			if(user != null){
				Model.userName = user.getDisplayName();
				Model.profile_pic_url =user.getImage().getUrl();
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
//				intent.putExtra("loginType", "g");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
				startActivity(intent);
				
			}else{
				mPlusClient.disconnect();
			}
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
	
}
