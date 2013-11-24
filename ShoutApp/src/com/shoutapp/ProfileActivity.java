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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	int scrollX = 0;
	int scrollY = 0;

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
				int a = 5;
				scrollX = scrollv.getScrollX();
				scrollY = scrollv.getScrollY();
				tabPageChanged = true;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				int a = 5;
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				int a = 5;
				
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
	
	public class BadgeAdapter extends ArrayAdapter<BadgeObject>{
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
	
	private void requestDisallowParentInterceptTouchEvent(View __v, Boolean __disallowIntercept) {
	    while (__v.getParent() != null && __v.getParent() instanceof View) {
	        if (__v.getParent() instanceof ScrollView) {
	            __v.getParent().requestDisallowInterceptTouchEvent(__disallowIntercept);
	        }
	        __v = (View) __v.getParent();
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
                	ListView postListView = (ListView)v.findViewById(R.id.profile_tab_list_view);
                	if(position == 0){
                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, Model.getPostPreviews()));
                	}else if(position == 1){
//                		postListView.setAdapter(new PostPreviewAdapter(cxt, R.id.post_list_view, Model.getPostPreviews()));
                		postListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, Model.getBadge()));
                	}else if(position == 2){
                		postListView.setAdapter(new BadgeAdapter(cxt, R.id.post_list_view, Model.getBadge()));
                	}

                	postListView.setOnTouchListener(new OnTouchListener() {
						
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
