package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request.GraphUserCallback;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;
import com.shoutapp.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivityX extends FragmentActivity {

	private static final String TAG = "MainFragment";
	TextView userInfoTextView;
	ProfilePictureView pic;
	LoginButton loginbtn;
	private UiLifecycleHelper uiHelper;
	ImageView gp_photo;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private Activity login_activity;
	private ProgressDialog mConnectionProgressDialog;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	private View.OnClickListener gp_onClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.gp_login /*&& !mPlusClient.isConnected() && mConnectionResult != null*/){
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
		
		private Bitmap getImageBitmap(String url) {
	        Bitmap bm = null;
	        try {
	            URL aURL = new URL(url);
	            URLConnection conn = aURL.openConnection();
	            conn.connect();
	            InputStream is = conn.getInputStream();
	            BufferedInputStream bis = new BufferedInputStream(is);
	            bm = BitmapFactory.decodeStream(bis);
	            bis.close();
	            is.close();
	       } catch (IOException e) {
	           Log.e(TAG, "Error getting bitmap", e);
	       }
	       return bm;
	    }
		
		@Override
		public void onConnected(Bundle connectionHint) {
			Person user = mPlusClient.getCurrentPerson();
			if(user != null){
				String name = user.getDisplayName();
				String userId = user.getId();
				Image photo = user.getImage();
				gp_photo.setImageBitmap(getImageBitmap(photo.getUrl()));
			    
				TextView userInfoTextView = (TextView)findViewById(R.id.userInfoTextView);
				
				StringBuilder userInfo = new StringBuilder("");
				userInfo.append(name);
				userInfo.append(userId);
				userInfoTextView.setText(userInfo);
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		login_activity = this;
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
		
		// aþaðýdaki kodun log'a bastýðý hash key'i facebook'a girmek lazým
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.shoutapp", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
//	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {
	    	Log.d("NameNotFoundException : " , e.getMessage());
	    } catch (NoSuchAlgorithmException e) {
	    	Log.d("NoSuchAlgorithmException : " , e.getMessage());
	    }
		
		loginbtn = (LoginButton)findViewById(R.id.fb_login);
	    loginbtn.setFragment(getSupportFragmentManager().findFragmentById(R.id.fb_login));
	    loginbtn.setReadPermissions(Arrays.asList("email", "user_photos","user_birthday"));
	    userInfoTextView = (TextView) findViewById(R.id.userInfoTextView);
	    pic = (ProfilePictureView)findViewById(R.id.profilePictureView);
	    gp_photo = (ImageView)findViewById(R.id.imageView);
	    SignInButton gp_login = (SignInButton)findViewById(R.id.gp_login);
        for (int i = 0; i < gp_login.getChildCount(); i++) {
            View v = gp_login.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText("Log in with Google");
            }
        }
        
        mPlusClient = new PlusClient.Builder(this, gp_connectionCallback, gp_OnConnectionFailedListener)
        .setVisibleActivities("http://schemas.google.com/AddActivity","http://schemas.google.com/ListenActivity").build();
        ((SignInButton)findViewById(R.id.gp_login)).setOnClickListener(gp_onClick);
        
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");
        
	}
	
	protected void onStart(){
		super.onStart();
		mPlusClient.connect();
	}
	
	protected void onStop(){
		super.onStop();
		mPlusClient.disconnect();
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        Request.newMeRequest(session, new GraphUserCallback() {
				
				@Override
				public void onCompleted(GraphUser user, Response response) {
					// TODO Auto-generated method stub
					StringBuilder userInfo = new StringBuilder("");
					userInfo.append(String.format("Name: %s\n\n", user.getName()));
					userInfo.append(String.format("Birthday: %s\n\n", user.getBirthday()));
					userInfo.append((String)(user.getProperty("email")));
					String userId=user.getId();
					pic.setProfileId(userId);
					userInfoTextView.setText(userInfo);
				}
			}).executeAndWait();//.executeAsync();
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        userInfoTextView.setText("");
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null && (session.isOpened() || session.isClosed())) {
	    	onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	    
	    if(requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK){
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

	
}
