package com.shoutapp.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.util.Log;

public class FetchJsonTask<T> extends AsyncTask<Object, Void, T> {
	private Class<T> object;
	private String path;
	private Callback<T> callback;

	public FetchJsonTask(Class<T> object, String path, Callback<T> callback) {
		this.object = object;
		this.path = path;
		this.callback = callback;
	}

	@Override
	protected void onPreExecute() {
		callback.onStart();
	}

	@Override
	protected void onPostExecute(T result) {
		if (null == result) {
			callback.onFail();
		} else {
			callback.onSuccess(result);
		}
	}

	@Override
	protected T doInBackground(Object... params) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.URL + path);
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			for (int i = 0; i < params.length; i = i + 2) {
				String name = params[i].toString();
				String value = params[i + 1].toString();
				nameValuePairs.add(new BasicNameValuePair(name, value));
			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			if ( response.getStatusLine().getStatusCode() != 200){
				// Meaning an error has occured
				return null;
			}
			InputStream is = response.getEntity().getContent();					
			// Compatible with JavaScript's Date format
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'").create();
			Reader reader = new InputStreamReader(is);
			// Parse the fetched JSON object to a Java object 
			T myObj = gson.fromJson(reader, object);
			Log.d("Recieved Object", myObj.toString());
			return myObj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public interface Callback<T> {
		public abstract void onStart();

		public abstract void onSuccess(T obj);

		public abstract void onFail();
	}
}
