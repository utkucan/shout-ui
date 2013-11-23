package com.shoutapp;

import java.util.ArrayList;

import com.shoutapp.MainActivity.PostPreviewAdapter;
import com.shoutapp.MainActivity.SwipePagerAdapter;
import com.shoutapp.RefreshableListView.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class ProfileActivity extends BaseActivity{

	private Context cxt;
	ViewPager pager;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		View profileView = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile, null);
		RelativeLayout profileLayout = (RelativeLayout)profileView.findViewById(R.id.profile_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
											      RelativeLayout.LayoutParams.WRAP_CONTENT,
											      RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW,R.id.topBar);
        profileLayout.setLayoutParams(lp);
        mainLayout.addView(profileLayout);
        
        pager = (ViewPager)profileLayout.findViewById(R.id.profile_pager);
        
        PagerTabStrip strip = PagerTabStrip.class.cast(findViewById(R.id.profile_tabs));
        strip.setDrawFullUnderline(false);
        strip.setTabIndicatorColorResource(R.color.profile_tab_hihglight_color);
//        strip.setNonPrimaryAlpha(0.5f);
//        strip.setTextSpacing(25);
//        strip.setTextColor(0x00666666);
//        strip.setTextColor(0x00ff0000);
//        android:background="#eeeeee"
        strip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        pager.setAdapter(new SwipeTabAdapter());

	}
	
	public class PostPreviewAdapter extends ArrayAdapter<PostPreviewItemObject> {
        
		public PostPreviewAdapter(Context context, int textViewResourceId,ArrayList list) {
			super(context, textViewResourceId,list);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item_preview, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.post_title);
			title.setText(getItem(position).title);
			
			TextView category = (TextView) convertView.findViewById(R.id.category);
			category.setText(getItem(position).category);
			
			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(getItem(position).time);
			
			TextView distance = (TextView) convertView.findViewById(R.id.distance);
			distance.setText(getItem(position).distance);
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
			        i.setClassName("com.shoutapp", "com.shoutapp.PostItemViewActivity");
			        startActivity(i);
				}
			});
			
			return convertView;
		}

	}
	
	public class Badge{
		public int imageId;
		public String date;
		public String desc;
		public Badge(int imageId,String date,String desc){
			this.imageId = imageId;
			this.date = date;
			this.desc = desc;
		}
	}
	
	public class BadgeAdapter extends ArrayAdapter<Badge>{
		public BadgeAdapter(Context context, int textViewResourceId,ArrayList list) {
			super(context, textViewResourceId,list);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.badge_list_item, null);
			}
			ImageView image = (ImageView) convertView.findViewById(R.id.badge_image);
			int id = getItem(position).imageId;
			image.setImageResource(getItem(position).imageId);//(R.drawable.events_cat_bicycle);
			
			TextView description = (TextView) convertView.findViewById(R.id.badge_description);
			description.setText(getItem(position).desc);
			
			TextView time = (TextView) convertView.findViewById(R.id.badege_receive_time);
			time.setText(getItem(position).date);

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
                	View v = LayoutInflater.from(getBaseContext()).inflate(R.layout.profile_tab_list_layout, null);;
                	if(position == 0){
                		ListView postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, Model.getPostPreviews()));
                	}else if(position == 1){
                		ListView postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, Model.getPostPreviews()));
                	}else if(position == 2){
                		ListView postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
                		ArrayList<Badge> badges = new ArrayList<ProfileActivity.Badge>();
                		badges.add(new Badge(R.drawable.events_cat_bicycle, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_camp, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_food, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_game, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_movie, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_night, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_uknowwhatimean, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_cat_wood, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_1, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_10, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_25, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_50, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_100, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_250, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_500, "23.11.2013", "hobarey"));
                		badges.add(new Badge(R.drawable.events_count_king, "23.11.2013", "hobarey"));
                		postListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, badges));
                	}
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
