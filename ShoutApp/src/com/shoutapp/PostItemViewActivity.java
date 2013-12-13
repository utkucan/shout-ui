package com.shoutapp;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PostItemViewActivity extends BaseActivity{

	//	ListView comments;
	LinearLayout comment_list_lay;
	ScrollView scrollv;
	RelativeLayout mapLay,rateEditLayout = null;
	boolean isMapLayHeightSet = false;
	ImageButton rateEditBtn = null;
	private GoogleMap map;
	LatLng loc;
	int eventId;
	String eventOwner;
	Event e;
	Context cxt;
	TextView title_view,category_view,time_view, distance_view, description_view, owner_view;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
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
		title_view = ((TextView)findViewById(R.id.post_item_title));
		category_view =(TextView)findViewById(R.id.post_item_category);
		time_view=(TextView)findViewById(R.id.post_item_time);
		distance_view = (TextView)findViewById(R.id.post_item_distance);
		description_view = (TextView)findViewById(R.id.post_item_description);
		owner_view = (TextView)findViewById(R.id.post_item_owner);
		
		title_view.setText("");
		category_view.setText("");
		time_view.setText("");
		distance_view.setText("");
		description_view.setText("");
		owner_view.setText("");

		Bundle extras = getIntent().getExtras();
		eventId = extras.getInt("eventId");
		eventOwner = extras.getString("owner");
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.post_on_map)).getMap();
		(new GetEventDetails(eventOwner, eventId, new RespCallback() {

			@Override
			public void callback_events(ArrayList<Event> Events) {
				// TODO Auto-generated method stub
				if(Events.size()>0){
					e = Events.get(0);
					
					loc = new LatLng(e.latitute,e.longtitute);
					title_view.setText(e.title);
					category_view.setText((new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Categories)))).get(e.category));
					time_view.setText(e.time);
					distance_view.setText(e.distance(cxt)+" km");
					description_view.setText(e.description);
					owner_view.setText(e.creator_id);
					
					for (Comment c : Events.get(0).comments) {
						addCommentPreview(c);
					}
					
					map.addMarker(new MarkerOptions().position(loc).title("You are here!"));
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
					map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
				}
				else{
					AlertDialog ad = (new Builder(cxt)).create();
					ad.setMessage("The post was canceled");
					ad.setCancelable(false);
					ad.setButton(android.content.DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							onBackPressed();
						}
					});
					ad.show();
				}
			}

			@Override
			public void callback_ack() {}
		})).execute();


		rateEditBtn = (ImageButton)findViewById(R.id.rate_btn);
		rateEditLayout = (RelativeLayout)findViewById(R.id.rate_btn_holder);

		if(User.hash == eventOwner){
			rateEditBtn.setBackgroundResource(R.drawable.trash);
			rateEditBtn.setOnClickListener(deleteClickListener);
			rateEditLayout.setOnClickListener(deleteClickListener);
		}else{
			rateEditBtn.setOnClickListener(rateClickListener);
			rateEditLayout.setOnClickListener(rateClickListener);
		}

		owner_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", e.creator_id);			
				cxt.startActivity(intent);
			}
		});
		
		ImageButton addCommentBtn = (ImageButton)findViewById(R.id.add_comment_btn);
		addCommentBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(v.getContext(), "tamaaaaam1", Toast.LENGTH_SHORT).show();
				RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
				View comm = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_comment_xml, null);
				RelativeLayout seekLayout = (RelativeLayout)comm.findViewById(R.id.commentAddLayout);

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.FILL_PARENT,
						RelativeLayout.LayoutParams.FILL_PARENT);
				seekLayout.setLayoutParams(lp);
				mainLayout.addView(comm);						
				Toast.makeText(v.getContext(), "tamaaaaam", Toast.LENGTH_SHORT).show();
			}
		});
				
	}

	private OnClickListener deleteClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			//			delete event

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
			
		}
	};

	private OnClickListener shareClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private void addCommentPreview(/*CommentItemObjet*/ Comment object){
		View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);

		((TextView) comment_item.findViewById(R.id.comment_text)).setText(object.content);
		TextView comment_owner = (TextView) comment_item.findViewById(R.id.comment_owner);
		comment_owner.setText(object.userName);
		((TextView) comment_item.findViewById(R.id.comment_owner_id)).setText(object.userId);
		((TextView) comment_item.findViewById(R.id.comment_time)).setText(object.getTime());

		comment_owner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				View parent = (View)v.getParent();
				String commnet_owner_id = ((TextView)parent.findViewById(R.id.comment_owner_id)).getText().toString();
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", commnet_owner_id);			
				cxt.startActivity(intent);
			}
		});
		
		comment_list_lay.addView(comment_item);
	}
}
