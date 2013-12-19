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
import com.shoutapp.entity.Comment;
import com.shoutapp.entity.Event;
import com.shoutapp.entity.FetchJsonTask.Callback;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class PostItemViewActivity extends BaseActivity {

	// ListView comments;
	LinearLayout comment_list_lay;
	ScrollView scrollv;
	RelativeLayout mapLay, rateEditLayout = null, shareBtnLayout = null;
	boolean isMapLayHeightSet = false;
	ImageButton rateEditBtn = null, shareBtn = null;
	private GoogleMap map;
	LatLng loc;
	int eventId;
	String eventOwner;
	Event event;
	Context cxt;
	TextView title_view, category_view, time_view, distance_view, description_view, owner_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cxt = this;
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.post_item_preview, null);
		// View layout =
		// LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item_preview,
		// null);
		RelativeLayout post_item_view_layout = (RelativeLayout) layout.findViewById(R.id.post_item_preview_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, R.id.topBar);
		post_item_view_layout.setLayoutParams(lp);
		mainLayout.addView(post_item_view_layout);
		comment_list_lay = (LinearLayout) findViewById(R.id.comment_list_layout);
		scrollv = (ScrollView) findViewById(R.id.post_preview_scrollView);
		mapLay = (RelativeLayout) findViewById(R.id.post_map);
		ViewTreeObserver vto = scrollv.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (!isMapLayHeightSet) {
					int h = scrollv.getMeasuredHeight();
					mapLay.getLayoutParams().height = (2 * h) / 5;
					isMapLayHeightSet = true;
					scrollv.scrollTo(0, 0);
				}
				return true;
			}
		});
		title_view = ((TextView) findViewById(R.id.post_item_title));
		category_view = (TextView) findViewById(R.id.post_item_category);
		time_view = (TextView) findViewById(R.id.post_item_time);
		distance_view = (TextView) findViewById(R.id.post_item_distance);
		description_view = (TextView) findViewById(R.id.post_item_description);
		owner_view = (TextView) findViewById(R.id.post_item_owner);

		title_view.setText("");
		category_view.setText("");
		time_view.setText("");
		distance_view.setText("");
		description_view.setText("");
		owner_view.setText("");

		Bundle extras = getIntent().getExtras();
		eventId = extras.getInt("eventId");
		//eventOwner = extras.getInt("owner");

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.post_on_map)).getMap();

		Event.fetchEventDetails(eventId, new Callback<Event>() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(com.shoutapp.entity.Event obj) {
				event = obj;
				loc = new LatLng(obj.getLat(), obj.getLon());
				title_view.setText(obj.getTitle());
				category_view.setText((new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Categories)))).get(obj.getCategory()));
				time_view.setText(obj.getCreationTime().toGMTString()); // TODO:
																		// Fix
				distance_view.setText(obj.distance(cxt) + " km");
				description_view.setText(obj.getDescription());
				owner_view.setText(obj.getCreator());

				for (Comment c : obj.getComments()) {
					addCommentPreview(c);
				}

				map.addMarker(new MarkerOptions().position(loc).title(event.getTitle()));
				map.setMyLocationEnabled(true);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));

				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			}

			@Override
			public void onFail() {
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
		});

		rateEditBtn = (ImageButton) findViewById(R.id.rate_btn);
		rateEditLayout = (RelativeLayout) findViewById(R.id.rate_btn_holder);

		if (User.hash == eventOwner) {
			rateEditBtn.setBackgroundResource(R.drawable.trash);
			rateEditBtn.setOnClickListener(deleteClickListener);
			rateEditLayout.setOnClickListener(deleteClickListener);
		} else {
			rateEditBtn.setOnClickListener(rateClickListener);
			rateEditLayout.setOnClickListener(rateClickListener);
		}

		owner_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", event.getCreator());
				cxt.startActivity(intent);
			}
		});

		ImageButton addCommentBtn = (ImageButton) findViewById(R.id.add_comment_btn);
		addCommentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (findViewById(R.id.add_comment_layout).isShown()) {
					return;
				}
				LinearLayout ln = (LinearLayout) findViewById(R.id.add_comment_layout);
				ScrollView sv = (ScrollView) findViewById(R.id.post_preview_scrollView);
				sv.fullScroll(View.FOCUS_DOWN);
				ln.setVisibility(View.VISIBLE);
				View comm = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_comment_xml, null);
				ln.addView(comm);

				Button submitCommentBtn = (Button) findViewById(R.id.submitCommentBtn);
				submitCommentBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						EditText textBox = (EditText) findViewById(R.id.addCommentInput);
						Log.d("SubmitComment", "submittin comment for event: " + eventId + " username: " + User.username);
						/*
						 * new AddComment(new Comment(User.hash, User.username,
						 * textBox.getText().toString()), eventId, new
						 * RespCallback() {
						 * 
						 * @Override public void
						 * callback_events(ArrayList<Event> Events) {
						 * 
						 * }
						 * 
						 * @Override public void callback_ack() { LinearLayout
						 * ln = (LinearLayout)
						 * findViewById(R.id.add_comment_layout); View remove =
						 * (View) ln.findViewById(R.layout.add_comment_xml); //
						 * View comm = //
						 * LayoutInflater.from(getBaseContext()).inflate
						 * (R.layout.add_comment_xml, // null);
						 * ln.removeAllViews(); ln.setVisibility(View.GONE); }
						 * }).execute();
						 */
					}

				});

			}
		});

		shareBtn = (ImageButton) findViewById(R.id.share_btn);
		shareBtnLayout = (RelativeLayout) findViewById(R.id.share_btn_holder);
		shareBtn.setOnClickListener(shareClickListener);
		shareBtnLayout.setOnClickListener(shareClickListener);
	}

	private OnClickListener deleteClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			// delete event

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
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Someone share a shout with you bayb!");
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share Shout To"));
		}
	};

	private void addCommentPreview(Comment comment) {
		View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);

		((TextView) comment_item.findViewById(R.id.comment_text)).setText(comment.getContent());
		TextView comment_owner = (TextView) comment_item.findViewById(R.id.comment_owner);
		comment_owner.setText(comment.getName());
		// ((TextView) comment_item.findViewById(R.id.comment_owner_id)).setText(comment.getUserId());
		((TextView) comment_item.findViewById(R.id.comment_time)).setText("Zamaneklencek");

		comment_owner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View parent = (View) v.getParent();
				String commnet_owner_id = ((TextView) parent.findViewById(R.id.comment_owner_id)).getText().toString();
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", commnet_owner_id);
				cxt.startActivity(intent);
			}
		});

		comment_list_lay.addView(comment_item);
	}
}
