package com.shoutapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shoutapp.entity.Comment;
import com.shoutapp.entity.Constants;
import com.shoutapp.entity.Event;
import com.shoutapp.entity.FetchJsonTask.Callback;
import com.shoutapp.entity.Status;

public class PostItemViewActivity extends BaseActivity {

	// ListView comments;
	LinearLayout comment_list_lay;
	ScrollView scrollv;
	RelativeLayout mapLay, rateEditLayout = null, shareBtnLayout = null;
	boolean isMapLayHeightSet = false;
	boolean isLoadedBefore = false;
	ImageButton rateEditBtn = null, shareBtn = null;
	private GoogleMap map;
	LatLng loc;
	int eventId;
	int eventOwner;
	Event event;
	EditText textBox;
	Context cxt;
	TextView title_view, category_view, time_view, distance_view, description_view, owner_view, rating_view;

	private OnClickListener deleteClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Event.removeEvent(User.hash, eventId, new Callback<Status>() {
				@Override
				public void onFail() {
					Toast.makeText(cxt, "post silinirken s�k�nt� oldu!", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onStart() {

				}

				@Override
				public void onSuccess(Status obj) {
					Toast.makeText(cxt, "Your post is successfully removed!", Toast.LENGTH_LONG).show();
					onBackPressed();
				}
			});
		}
	};

	private OnClickListener rateClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Event.upvoteEvent(User.hash, eventId, new Callback<Status>() {

				@Override
				public void onFail() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(Status obj) {
					// TODO Auto-generated method stub
					
				}
			});
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
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Shout! " + Constants.URL + "event/" + event.getId());
			sendIntent.setType("text/plain");
			startActivity(Intent.createChooser(sendIntent, "Share Shout To"));
		}
	};

	private void addCommentPreview(Comment comment) {
		View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);

		((TextView) comment_item.findViewById(R.id.comment_text)).setText(comment.getContent());
		TextView comment_owner = (TextView) comment_item.findViewById(R.id.comment_owner);
		comment_owner.setText(comment.getName());
		// ((TextView)
		// comment_item.findViewById(R.id.comment_owner_id)).setText(comment.getUserId());
		((TextView) comment_item.findViewById(R.id.comment_time)).setText(comment.getDateString());
		final int commentingUserId = comment.getUserId();
		comment_owner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", commentingUserId);
				cxt.startActivity(intent);
			}
		});

		comment_list_lay.addView(comment_item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cxt = this;
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

		isLoadedBefore = false;

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.post_item_preview, null);
		RelativeLayout post_item_view_layout = (RelativeLayout) layout.findViewById(R.id.post_item_preview_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, R.id.topBar);
		post_item_view_layout.setLayoutParams(lp);
		mainLayout.addView(post_item_view_layout);
		comment_list_lay = (LinearLayout) findViewById(R.id.comment_list_layout);
		scrollv = (ScrollView) findViewById(R.id.post_preview_scrollView);
		mapLay = (RelativeLayout) findViewById(R.id.post_map);
		ViewTreeObserver vto = scrollv.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
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
		rating_view = (TextView) findViewById(R.id.post_item_rating);
		distance_view = (TextView) findViewById(R.id.post_item_distance);
		description_view = (TextView) findViewById(R.id.post_item_description);
		owner_view = (TextView) findViewById(R.id.post_item_owner);


		title_view.setText("");
		category_view.setText("");
		time_view.setText("");
		distance_view.setText("");
		description_view.setText("");
		owner_view.setText("");
		rating_view.setText("");

		Bundle extras = getIntent().getExtras();
		eventId = extras.getInt("eventId");
		// eventOwner = extras.getInt("owner");

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.post_on_map)).getMap();

		appendComments();

		rateEditBtn = (ImageButton) findViewById(R.id.rate_btn);
		rateEditLayout = (RelativeLayout) findViewById(R.id.rate_btn_holder);

		owner_view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(cxt, ProfileActivity.class);
				intent.putExtra("profileId", event.getCreatorid());
				cxt.startActivity(intent);
			}
		});

		ImageButton addCommentBtn = (ImageButton) findViewById(R.id.add_comment_btn);
		addCommentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// scrollv.scrollTo(0, scrollv.getHeight());

				if (findViewById(R.id.add_comment_layout).isShown()) {
					scrollv.fullScroll(View.FOCUS_DOWN);
					return;
				}

				LinearLayout ln = (LinearLayout) findViewById(R.id.add_comment_layout);
				ln.setVisibility(View.VISIBLE);
				View comm = LayoutInflater.from(getBaseContext()).inflate(R.layout.add_comment_xml, null);

				ViewTreeObserver vto = scrollv.getViewTreeObserver();
				vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						scrollv.fullScroll(View.FOCUS_DOWN);
						// ((EditText)
						// findViewById(R.id.addCommentInput)).requestFocus();
						return true;
					}
				});
				ln.addView(comm);
				scrollv.fullScroll(View.FOCUS_DOWN);

				Button submitCommentBtn = (Button) findViewById(R.id.submitCommentBtn);
				submitCommentBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						textBox = (EditText) findViewById(R.id.addCommentInput);
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(textBox.getWindowToken(), 0);

						// textBox.setInputType(0);
						Log.d("SubmitComment", "submittin comment for event: " + eventId + " username: " + User.username);
						Comment.addComment(User.hash, eventId, textBox.getText().toString(), new Callback<Status>() {

							@Override
							public void onFail() {
								// TODO: Copy of the onSuccess?
							}

							@Override
							public void onStart() {
							}

							@Override
							public void onSuccess(Status obj) {
								LinearLayout ln = (LinearLayout) findViewById(R.id.add_comment_layout);
								// View remove =
								// ln.findViewById(R.layout.add_comment_xml);
								// View comm =
								// LayoutInflater.from(getBaseContext()).inflate(R.layout.add_comment_xml,
								// null);
								ln.removeAllViews();
								ln.setVisibility(View.INVISIBLE);
								appendComments();
								// scrollv.scrollTo(0, scrollv.getHeight());
								isLoadedBefore = true;
							}
						});
					}

				});

			}
		});

		shareBtn = (ImageButton) findViewById(R.id.share_btn);
		shareBtnLayout = (RelativeLayout) findViewById(R.id.share_btn_holder);
		shareBtn.setOnClickListener(shareClickListener);
		shareBtnLayout.setOnClickListener(shareClickListener);
	}

	private void appendComments() {
		Event.fetchEventDetails(eventId, new Callback<Event>() {
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

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(Event obj) {
				event = obj;
				loc = new LatLng(obj.getLat(), obj.getLon());
				title_view.setText(obj.getTitle());
				category_view.setText((new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.Categories)))).get((int) (Math.log10(obj.getCategory())/Math.log10(2))));
				time_view.setText(obj.getDateString());
				distance_view.setText(obj.distance(cxt));
				description_view.setText(obj.getDescription());
				owner_view.setText(obj.getCreatorName());

				rating_view.setText("" + obj.getRating());
				
				eventOwner = obj.getCreatorid();

				comment_list_lay.removeAllViews();

				for (Comment c : obj.getComments()) {
					addCommentPreview(c);
				}

				Log.d("Userid, eventowner", User.user_id + "-" + eventOwner);
				if (User.user_id == eventOwner) {// || true
					rateEditBtn.setBackgroundResource(R.drawable.trash);
					rateEditBtn.setOnClickListener(deleteClickListener);
					rateEditLayout.setOnClickListener(deleteClickListener);
				} else {
					rateEditBtn.setOnClickListener(rateClickListener);
					rateEditLayout.setOnClickListener(rateClickListener);
				}

				map.addMarker(new MarkerOptions().position(loc).title(event.getTitle()));
				map.setMyLocationEnabled(true);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));

				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
				if (isLoadedBefore)
					scrollv.fullScroll(View.FOCUS_DOWN);
			}
		});

	}
}
