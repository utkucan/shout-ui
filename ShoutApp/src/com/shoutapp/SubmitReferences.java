package com.shoutapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class SubmitReferences extends AsyncTask<Void, Void, Void> {

	String res ="";
	RespCallback resCall;
	int eventId;
	String preferences; int distance, time;
	
	
	public SubmitReferences(String preferences, int distance, int time, RespCallback resCall){
		this.preferences = preferences;
		this.distance = distance;
		this.time = time;
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/submitPreferences/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
		nameValuePair.add(new BasicNameValuePair("distance", "" + distance ));
		nameValuePair.add(new BasicNameValuePair("time", "" + time));
		nameValuePair.add(new BasicNameValuePair("preferences", preferences));
		nameValuePair.add(new BasicNameValuePair("hash", "" + User.hash ));

		// Url Encoding the POST parameters
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		}
		catch (UnsupportedEncodingException e) {
			// writing error to Log
			e.printStackTrace();
		}
		try {
			HttpResponse response = httpClient.execute(httpPost);
			BufferedReader inBuffer = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator");
			while ((line = inBuffer.readLine()) != null) {
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}
			



		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		resCall.callback_ack();
	}
}