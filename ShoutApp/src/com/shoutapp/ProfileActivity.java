package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;
import com.shoutapp.MainActivity.PostPreviewAdapter;
import com.shoutapp.MainActivity.SwipePagerAdapter;
import com.shoutapp.RefreshableListView.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ProfileActivity extends BaseActivity{

	private Context cxt;
	ViewPager pager;
	ScrollView scrollv;
	boolean isTabLayHeightSet = false;
	RelativeLayout profileLayout;
	boolean tabPageChanged = false;
	ImageButton add_post_btn;
	int scrollX = 0;
	int scrollY = 0;
	ListView postListView;
	//	ImageView profilePic;
	//	public PlusClient mPlusClient;
	//	private final int[] scrollstate = { 0,0,0};
	//	private final int[] topVisibleItems = { 0,0,0};
	//	private final ListView[] lvs = {null,null,null};

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
		//		mPlusClient = super.mPlusClient;
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		View profileView = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile, null);
		profileLayout = (RelativeLayout)profileView.findViewById(R.id.profile_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		profileLayout.setLayoutParams(lp);
		mainLayout.addView(profileLayout);

		add_post_btn = (ImageButton)findViewById(R.id.profile_add_post_btn);
		add_post_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClassName("com.shoutapp", "com.shoutapp.AddPostActivity");
				startActivity(i);
			}
		});

		scrollv = (ScrollView)findViewById(R.id.scrollView1);

		//        scrollv.setOnTouchListener(new OnTouchListener() {
		//			
		//			@Override
		//			public boolean onTouch(View arg0, MotionEvent arg1) {
		//				// TODO Auto-generated method stub
		//				int index = pager.getCurrentItem();
		//				ListView lv = lvs[index];
		//				if( !( (lv.getLastVisiblePosition() >= lv.getCount() && scrollstate[index]<0) || (lv.getFirstVisiblePosition() <= 0 && scrollstate[index]>0) ) ){
		//					scrollv.requestDisallowInterceptTouchEvent(true);
		//				}
		//				return false;
		//			}
		//		});

		pager = (ViewPager)profileLayout.findViewById(R.id.profile_pager);

		PagerTabStrip strip = PagerTabStrip.class.cast(findViewById(R.id.profile_tabs));
		strip.setDrawFullUnderline(false);
		strip.setTabIndicatorColorResource(R.color.profile_tab_hihglight_color);
		strip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
		pager.setAdapter(new SwipeTabAdapter());
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				scrollX = scrollv.getScrollX();
				scrollY = scrollv.getScrollY();
				tabPageChanged = true;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

		ViewTreeObserver vto = scrollv.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if(!isTabLayHeightSet){
					int h = scrollv.getMeasuredHeight();
					pager.getLayoutParams().height = h-13;
					isTabLayHeightSet = true;
					scrollv.scrollTo(0, 0);
				}
				if(tabPageChanged){
					int X = scrollv.getScrollX();
					int Y = scrollv.getScrollY();
					if(scrollX != X || scrollY != Y)
						scrollv.scrollTo(scrollX, scrollY);
					else
						tabPageChanged = false;
				}
				return true;
			}
		});

		//        setOnGoogleConnectionStateChangeEvent(new OnGoogleConnectionStateChangeEventListener() {
			//			
			//			@Override
			//			public void onDisconnect() {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//			
		//			@Override
		//			public void onConnect() {
		//				// TODO Auto-generated method stub
		//				Person user = mPlusClient.getCurrentPerson();
		//				profilePic = (ImageView)findViewById(R.id.profile_pic);
		//		        Image photo = user.getImage();
		//		        profilePic.setImageBitmap(getImageBitmap(photo.getUrl()));
		////		        profilePic.setImageBitmap(getImageBitmap(mPlusClient.getCurrentPerson().getImage().getUrl()));
		//			}
		//		});
		//        
		((TextView)findViewById(R.id.userName)).setText(Model.userName);
		new ProfilePhotoLoader().execute();
//		((ImageView)findViewById(R.id.profile_pic)).setImageBitmap(getImageBitmap(Model.profile_pic_url));
	}

	class ProfilePhotoLoader extends AsyncTask<Void, Void, Void> {

		private Exception exception;

		protected Void doInBackground(Void... params) {
			if(Model.profile_pic_url != ""){
				Bitmap bm = null;
				try {
					URL aURL = new URL(Model.profile_pic_url);
					URLConnection conn = aURL.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					bm = BitmapFactory.decodeStream(bis);
					bis.close();
					is.close();
					((ImageView)findViewById(R.id.profile_pic)).setImageBitmap(bm);
				} catch (Exception e) {
					Log.e("get profile pic", "Error getting bitmap", e);
				}
			}
			return null;
		}

		protected void onPostExecute() {
			// TODO: check this.exception 
			// TODO: do something with the feed
		}
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
			Log.e("get profile pic", "Error getting bitmap", e);
		}
		return bm;
	}

	public class PostPreviewAdapter extends ArrayAdapter<PostPreviewItemObject> {

		public PostPreviewAdapter(Context context, int textViewResourceId,ArrayList<PostPreviewItemObject> list) {
			super(context, textViewResourceId,list);
			list.add(new PostPreviewItemObject("", "", "", "",-1));
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if(getItem(position).title == ""){
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
				convertView.setVisibility(View.INVISIBLE);
			}
			else{
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
				TextView title = (TextView) convertView.findViewById(R.id.post_title);
				title.setText(getItem(position).title);

				TextView category = (TextView) convertView.findViewById(R.id.category);
				category.setText(getItem(position).category);

				TextView time = (TextView) convertView.findViewById(R.id.time);
				time.setText(getItem(position).time);

				TextView distance = (TextView) convertView.findViewById(R.id.distance);
				distance.setText(getItem(position).distance);

				convertView.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						if(arg1.getAction() == MotionEvent.ACTION_MOVE){
							scrollv.requestDisallowInterceptTouchEvent(true);
						}
						return false;
					}
				});

				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String title = (String) ((TextView) v.findViewById(R.id.post_title)).getText();
						String category = (String) ((TextView) v.findViewById(R.id.category)).getText();
						String time = (String) ((TextView) v.findViewById(R.id.time)).getText();
						String distance = (String) ((TextView) v.findViewById(R.id.distance)).getText();

						Intent intent = new Intent(getBaseContext(), PostItemViewActivity.class);
						intent.putExtra("title", title);
						intent.putExtra("category", category);
						intent.putExtra("time", time);
						intent.putExtra("distance", distance);
						startActivity(intent);

						//						Intent i = new Intent();
						//				        i.setClassName("com.shoutapp", "com.shoutapp.PostItemViewActivity");
						//				        startActivity(i);
					}
				});
			}
			return convertView;
		}

	}

	public class BadgeAdapter extends ArrayAdapter<BadgeObject>{

		//		private ViewGroup lv;
		//		private boolean isSet = false;

		public BadgeAdapter(Context context, int textViewResourceId,ArrayList<BadgeObject> list) {
			super(context, textViewResourceId,list);
			list.add(new BadgeObject(-1, "", ""));
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if(getItem(position).imageId < 0){	
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
				//				convertView.findViewById(R.id.badge_image).getLayoutParams().height = add_post_btn.getMeasuredHeight()-5;
				convertView.setVisibility(View.INVISIBLE);
			}else{
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.badge_list_item, null);
				ImageView image = (ImageView) convertView.findViewById(R.id.badge_image);
				image.setImageResource(getItem(position).imageId);//(R.drawable.events_cat_bicycle);

				TextView description = (TextView) convertView.findViewById(R.id.badge_description);
				description.setText(getItem(position).desc);

				TextView time = (TextView) convertView.findViewById(R.id.badege_receive_time);
				time.setText(getItem(position).date);
			}
			return convertView;
		}
	}

	public class NatificationAdapter extends ArrayAdapter<NotificationItemObject>{

		//		private ViewGroup lv;
		//		private boolean isSet = false;

		public NatificationAdapter(Context context, int textViewResourceId,ArrayList<NotificationItemObject> list) {
			super(context, textViewResourceId,list);
			list.add(new NotificationItemObject("", "", "",0));
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if(getItem(position).notificationText == ""){	
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
				convertView.setVisibility(View.INVISIBLE);
			}else{
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, null);
				TextView comment = (TextView) convertView.findViewById(R.id.comment_text);
				comment.setText("");
				String ownerName = getItem(position).owner;
				if(ownerName != ""){
					comment.setText(Html.fromHtml("<font color='#f37f77'><b>" + ownerName + "</b></font>"));
					comment.append(" ");
				}

				comment.append(getItem(position).notificationText);

				TextView owner = (TextView) convertView.findViewById(R.id.comment_owner);
				owner.setVisibility(View.GONE);
				TextView time = (TextView) convertView.findViewById(R.id.comment_time);
				time.setText(getItem(position).time);

			}
			return convertView;
		}
	}

	public class SwipeTabAdapter extends PagerAdapter{

		private final String[] titles = { "Notification", "Posts", "Badges" };

		@Override
		public int getCount() {
			return 3;
		}

		/**
		 * Create the page for the given position.  The adapter is responsible
		 * for adding the view to the container given here, although it only
		 * must ensure this is done by the time it returns from
		 * {@link #finishUpdate(android.view.ViewGroup)}.
		 *
		 * @param collection The containing View in which the page will be shown.
		 * @param position The page position to be instantiated.
		 * @return Returns an Object representing the new page.  This does not
		 * need to be a View, but can be some other container of the page.
		 */
		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile_tab_list_layout, null);
			postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
			if(position == 0){
				postListView.setAdapter(new NatificationAdapter(cxt, R.id.post_list_view, (ArrayList<NotificationItemObject>) Model.getNotifications().clone()));
				//                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, (ArrayList<PostPreviewItemObject>) Model.getPostPreviews().clone()));
			}else if(position == 1){
				GetMyEvents gmy = new GetMyEvents(User.hash, new RespCallback() {
					
					@Override
					public void callback_events(ArrayList<Event> Events) {
						// TODO Auto-generated method stub
						ArrayList<PostPreviewItemObject> objs = new ArrayList<PostPreviewItemObject>();
						for (Event e : Events) {
							objs.add(new PostPreviewItemObject(e.title,e.category+"",
									new SimpleDateFormat("MM/dd/yyyy hh:mm").format(e.creationDate),
									MainActivity.distance(e.latitute,e.longtitute,MainActivity.gpsObject.latitude,MainActivity.gpsObject.longitude)+" km",e.id));
						}
						postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view,objs));
					}
					
					@Override
					public void callback_ack() {
						// TODO Auto-generated method stub
						
					}
				});
				gmy.execute();
//				postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, (ArrayList<PostPreviewItemObject>) Model.getPostPreviews().clone()));
				//                		postListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, Model.getBadge()));
			}else if(position == 2){
				postListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, Model.getBadge()));
				//                		postListView.getLayoutParams().height += 75;
			}


			postListView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_MOVE){
						//								if(scrollv.getScrollY() < scrollv.getMaxScrollAmount())
						//									scrollv.smoothScrollTo(0, scrollv.getMaxScrollAmount());
						scrollv.requestDisallowInterceptTouchEvent(true);
					}
					return false;
				}
			});
			collection.addView(v,position);
			return v;
		}

		/**
		 * Remove a page for the given position.  The adapter is responsible
		 * for removing the view from its container, although it only must ensure
		 * this is done by the time it returns from {@link #finishUpdate(android.view.ViewGroup)}.
		 *
		 * @param collection The containing View from which the page will be removed.
		 * @param position The page position to be removed.
		 * @param view The same object that was returned by
		 * {@link #instantiateItem(android.view.View, int)}.
		 */
		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((RelativeLayout) view);
		}


		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}


		/**
		 * Determines whether a page View is associated with a specific key object
		 * as returned by instantiateItem(ViewGroup, int). This method is required
		 * for a PagerAdapter to function properly.
		 * @param view Page View to check for association with object
		 * @param object Object to check for association with view
		 * @return
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view==object);
		}


		/**
		 * Called when the a change in the shown pages has been completed.  At this
		 * point you must ensure that all of the pages have actually been added or
		 * removed from the container as appropriate.
		 * @param arg0 The containing View which is displaying this adapter's
		 * page views.
		 */
		@Override
		public void finishUpdate(ViewGroup arg0) {
			tabPageChanged = true;
		}


		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(ViewGroup arg0) {}

	}

}
