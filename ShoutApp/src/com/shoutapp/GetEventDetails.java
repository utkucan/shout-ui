package com.shoutapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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

public class GetEventDetails extends AsyncTask<Void, Void, Void> {
	private String user;

	String res ="";
	RespCallback resCall;
	ArrayList<Event> myEvents = new ArrayList<Event>();
	int eId;
	public GetEventDetails(String user, int id, RespCallback resCall) {
		this.user = user;
		eId = id;
		this.resCall = resCall;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://shoutaround.herokuapp.com/getEvent/");
		// Building post parameters, key and value pair
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
		nameValuePair.add(new BasicNameValuePair("eventId", "" + eId ));
		Log.d("LoadingEvent","event id : " + eId );
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
			int lineCount = 1;
			int eventCategory=0;
			String eventCreator_id = "";
			double eventLongtitute=0, eventLatitute=0, eventRadius=0;
			Date eventCreationDate = null, eventExpiredDate=null;
			String creatorName = "";
			String eventTitle="", desc = "";
			int numberOfComments = 0;
			Event e = null;
			while ((line = inBuffer.readLine()) != null) {

				if(lineCount == 1){
					eventLatitute = Double.parseDouble(line);
				}else if(lineCount ==2){
					eventLongtitute   = Double.parseDouble(line);
				}else if(lineCount ==3){
					eventCategory = Integer.parseInt(line);
				}else if(lineCount ==4){
					creatorName = line;// Integer.parseInt(line);
				}else if(lineCount ==5){
					eventCreator_id = line;// Integer.parseInt(line);
				}else if(lineCount ==6){
					eventTitle = line;
				}else if(lineCount ==7){
					//TODO: creationDate
					eventCreationDate = new Date(Long.parseLong(line));
				}else if(lineCount == 8){
					//TODO: expiredDate
					eventExpiredDate = new Date(Long.parseLong(line)); 
				}else if(lineCount ==9){
					desc = line;
					e = new Event(eventTitle,eventLongtitute, eventLatitute, eventRadius,eventCreationDate, eventExpiredDate, eventCategory, eventCreator_id);
					Log.d("EventID","event: "+ eId +" title: " +eventTitle);
					e.setId(eId);
					e.setDesc(desc);
				}else if(lineCount == 10){
					numberOfComments = Integer.parseInt(line);
					for(int i = 0; i<numberOfComments; i++){
						String userId = "";
						String userName = ""; String content = "";
						Date commentDate = null;
						for(int j = 0; j<4; j++){
							line = inBuffer.readLine();
							if(j == 0){
								userName = line;
							}else if(j == 1){
								userId = line;//Integer.parseInt(line);
							}else if(j == 2){
								commentDate = new Date(Long.parseLong(line)); ;//Integer.parseInt(line);
							}else{
								content = line;
							}
						}
						Comment c = new Comment(userId,userName,content);
						c.creationTime = commentDate;
						e.addComment(c); 
					}
				}   
				lineCount++;
				stringBuffer.append(line + newLine);
				Log.d("sadasda", line);
			}


			myEvents.add(e);

		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		resCall.callback_events(myEvents);
	}
}
