package com.shoutapp;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.shoutapp.entity.Event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
	String UserId;
	ListView postListView,notificationListView,badgeListView; 

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		View profileView = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile, null);
		profileLayout = (RelativeLayout)profileView.findViewById(R.id.profile_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.BELOW,R.id.topBar);
		profileLayout.setLayoutParams(lp);
		mainLayout.addView(profileLayout);
		
		Bundle extras = getIntent().getExtras();
		UserId=extras.getString("profileId");
		/*
		(new GetProfile(new ProfileCallback() {
			
			@Override
			public void callback_profilInfo(Profile profil) {
				// TODO Auto-generated method stub
				((TextView)findViewById(R.id.userName)).setText(profil.name);
				getProfilePhoto.execute(profil.picURL);
				((TextView)findViewById(R.id.location_name)).setText(profil.location);
				((TextView)findViewById(R.id.profile_rating)).setText(profil.popularity+"");
			}
		}, UserId)).execute();
		*/

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
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageScrollStateChanged(int arg0) {}
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
		
		
	}
	
	private AsyncTask<String, Void, Void> getProfilePhoto = new AsyncTask<String, Void, Void>() {
		
		@Override
		protected Void doInBackground(String... params) {
			if(params.length>0){
				String photoUrl = params[0];
				Bitmap bm = null;
				try {
					URL aURL = new URL(photoUrl);
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
	};

	public class MyEventPreviewAdapter extends EventPreviewAdapter {

		public MyEventPreviewAdapter(ListView listView,Context context, int textViewResourceId, Event[] list) {
			super(listView,context, textViewResourceId,list,null);
			// list.add(new Event("", 0, 0, 0, null, null,0, "")); TODO: bu ne
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = super.getView(position, convertView, parent);
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
			return convertView;
		}
	}

	public class BadgeAdapter extends ArrayAdapter<BadgeObject>{

		public BadgeAdapter(Context context, int textViewResourceId,ArrayList<BadgeObject> list) {
			super(context, textViewResourceId,list);
			list.add(new BadgeObject(-1, "", ""));
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if(getItem(position).imageId < 0){	
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.tab_list_empty_item, null);
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

		private String[] titles;// = { "Notification", "Posts", "Badges" };
		public SwipeTabAdapter(){
			if(User.hash.equals(UserId))
				titles = new String[] { "Notification", "Posts", "Badges" };
			else
				titles = new String[] { "Posts", "Badges" };
		}
		
		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile_tab_list_layout, null);
			ListView lv = null;
			if(titles[position] == "Notification"){ //notifications
				notificationListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
				notificationListView.setAdapter(new NatificationAdapter(cxt, R.id.post_list_view, (ArrayList<NotificationItemObject>) Model.getNotifications().clone()));
				lv = notificationListView;
				//                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, (ArrayList<PostPreviewItemObject>) Model.getPostPreviews().clone()));
			}else if(titles[position] == "Posts"){ // events
				postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
				lv = postListView;
				Bundle extras = getIntent().getExtras();
				UserId=extras.getString("profileId");
				
				/*
				GetEvents gmy = new GetEvents(UserId, new RespCallback() {

					@Override
					public void callback_events(ArrayList<Event> Events) {
						postListView.setAdapter(new MyEventPreviewAdapter(postListView,cxt, R.id.post_list_view,Events));
					}

					@Override
					public void callback_ack() {}
				}); 
				gmy.execute(); 
				*/
			}else if(titles[position] == "Badges"){ // badges
				badgeListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
				badgeListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, Model.getBadge()));
				lv = badgeListView;
			}
			lv.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_MOVE){
						scrollv.requestDisallowInterceptTouchEvent(true);
					}
					return false;
				}
			});
			collection.addView(v,position);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((RelativeLayout) view);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view==object);
		}

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
