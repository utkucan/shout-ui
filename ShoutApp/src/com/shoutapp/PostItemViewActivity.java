package com.shoutapp;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PostItemViewActivity extends BaseActivity{

	//	ListView comments;
	LinearLayout comment_list_lay;
	ScrollView scrollv;
	RelativeLayout mapLay;
	boolean isMapLayHeightSet = false;
	boolean isUserOwn = true;
	ImageButton rateEditBtn = null;
	RelativeLayout rateEditLayout = null; 
	private GoogleMap map;
	LatLng loc;
	int eventId;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.post_item_preview, null);
		//		View layout = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item_preview, null);
		RelativeLayout post_item_view_layout = (RelativeLayout)layout.findViewById(R.id.post_item_preview_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		post_item_view_layout.setLayoutParams(lp);
		mainLayout.addView(post_item_view_layout);
		comment_list_lay = (LinearLayout)findViewById(R.id.comment_list_layout);
		scrollv = (ScrollView)findViewById(R.id.post_preview_scrollView);       
		mapLay = (RelativeLayout)findViewById(R.id.post_map);
		ViewTreeObserver vto = scrollv.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if(!isMapLayHeightSet){
					int h = scrollv.getMeasuredHeight();
					mapLay.getLayoutParams().height = (2*h)/5;
					isMapLayHeightSet = true;
					scrollv.scrollTo(0, 0);
				}
				return true;
			}
		});

		Bundle extras = getIntent().getExtras();
		eventId = extras.getInt("eventId");
		loc = new LatLng(extras.getDouble("lat"), extras.getDouble("lon"));

		((TextView)findViewById(R.id.post_item_title)).setText(extras.getString("title"));
		((TextView)findViewById(R.id.post_item_category)).setText(extras.getString("category"));
		((TextView)findViewById(R.id.post_item_time)).setText(extras.getString("time"));
		((TextView)findViewById(R.id.post_item_distance)).setText(extras.getString("distance"));
		((TextView)findViewById(R.id.post_item_description)).setText(extras.getString("description"));
		((TextView)findViewById(R.id.post_item_owner)).setText(extras.getInt("owner")+"");


		GetEventDetails ged = new GetEventDetails(User.hash,eventId,new RespCallback() {

			@Override
			public void callback_events(ArrayList<Event> Events) {
				// TODO Auto-generated method stub
				for (Comment c : Events.get(0).comments) {
					addCommentPreview(new CommentItemObjet(
							c.content+"",
							c.userName+"",
							c.userId+""));
				}
			}

			@Override
			public void callback_ack() {
				// TODO Auto-generated method stub

			}
		});
		ged.execute();
		//        for(int i = 0; i< comment_list.size(); i++){
		//        	addCommentPreview(comment_list.get(i));
		//        }

		// duyurunun kime ait olduðu bilgisi alýnacak
		// eðer duyurunun sahibi bensem, rate butonu yerine edit butonu çýkýcak

		rateEditBtn = (ImageButton)findViewById(R.id.rate_btn);
		rateEditLayout = (RelativeLayout)findViewById(R.id.rate_btn_holder);

		if(isUserOwn){
			rateEditBtn.setBackgroundResource(R.drawable.ico_edit);
			rateEditBtn.setOnClickListener(editClickListener);
			rateEditLayout.setOnClickListener(editClickListener);
		}else{
			rateEditBtn.setOnClickListener(rateClickListener);
			rateEditLayout.setOnClickListener(rateClickListener);
		}
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.post_on_map)).getMap();
		map.addMarker(new MarkerOptions().position(loc).title("You are here!"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	private OnClickListener editClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			String title = (String) ((TextView)findViewById(R.id.post_item_title)).getText();
			String category = (String) ((TextView)findViewById(R.id.post_item_category)).getText();
			String time = (String) ((TextView)findViewById(R.id.post_item_time)).getText();
			String description = (String) ((TextView)findViewById(R.id.post_item_description)).getText();

			Intent intent = new Intent(getBaseContext(), AddPostActivity.class);
			intent.putExtra("title", title);
			intent.putExtra("category", category);
			intent.putExtra("time", time);
			intent.putExtra("description", description);
			startActivity(intent);

			//			Intent i = new Intent();
			//	        i.setClassName("com.shoutapp", "com.shoutapp.AddPostActivity");
			//	        startActivity(i);
		}
	};

	private OnClickListener rateClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener commentClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener shareClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private void addCommentPreview(CommentItemObjet object){
		View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);

		TextView comment = (TextView) comment_item.findViewById(R.id.comment_text);
		comment.setText(object.comment);

		TextView owner = (TextView) comment_item.findViewById(R.id.comment_owner);
		owner.setText(object.owner);

		TextView time = (TextView) comment_item.findViewById(R.id.comment_time);
		time.setText(object.time);

		comment_list_lay.addView(comment_item);
	}

	//	public class CommentItemObjet{
	//		public String comment;
	//		public String owner;
	//		public CommentItemObjet(String comment, String owner) {
	//			this.comment = comment; 
	//			this.owner = owner;
	//		}
	//	}



	//	int comment_count = 0;
	//	Handler timerHandler = new Handler();
	//    Runnable timerRunnable = new Runnable() {
	//
	//        @Override
	//        public void run() {
	//        	comment_count++;
	//        	View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);
	//			TextView comment = (TextView) comment_item.findViewById(R.id.comment_text);
	//			comment.setText(""+comment_count);
	//			
	//			TextView owner = (TextView) comment_item.findViewById(R.id.comment_owner);
	//			owner.setText("sercan");
	//			comment_list_lay.addView(comment_item);
	//			timerHandler.postDelayed(this, 3000);
	//        }
	//    };

}
