package com.shoutapp;

import java.util.ArrayList;

import com.shoutapp.RefreshableListView.OnRefreshListener;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity{

	private Context cxt;
	Activity currentactivity;
	ViewPager pager;
	RefreshableListView postListView;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
		currentactivity = this;
		
		RelativeLayout middleFrame = (RelativeLayout)findViewById(R.id.midle_frame);
		
		pager = new ViewPager(this);
		middleFrame.addView(pager, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
//		View swipe = LayoutInflater.from(getBaseContext()).inflate(R.layout.swipe, null);
//		RelativeLayout swipeLayout = (RelativeLayout)swipe.findViewById(R.id.swipe_layout);
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//											      RelativeLayout.LayoutParams.WRAP_CONTENT,
//											      RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.BELOW,R.id.topBar);
//        lp.addRule(RelativeLayout.ABOVE,R.id.post_view_bottom_bar);
//        swipeLayout.setLayoutParams(lp);
//        mainLayout.addView(swipe);
//        pager = (ViewPager)swipe.findViewById(R.id.page_swiper);
        SwipePagerAdapter adapter = new SwipePagerAdapter();
        pager.setAdapter(adapter);
        
        RelativeLayout change_view_btn_holder = (RelativeLayout)findViewById(R.id.change_view_btn_holder);
        change_view_btn_holder.setOnClickListener(changeViewClickListener);
        ImageButton change_view_btn = (ImageButton)findViewById(R.id.change_view_btn);
        change_view_btn.setOnClickListener(changeViewClickListener);

	}
	
	OnClickListener changeViewClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(pager.getCurrentItem()==0){
				pager.setCurrentItem(1, true);
				ImageButton btn = (ImageButton)v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.menu_icon);
			}else{
				pager.setCurrentItem(0, true);
				ImageButton btn = (ImageButton)v.findViewById(R.id.change_view_btn);
				btn.setBackgroundResource(R.drawable.map_icon);
			}
		}
	};
	ArrayList<SampleItem> items;
	public ArrayList<SampleItem> getPosts(){
		
		items = new ArrayList<MainActivity.SampleItem>();
//		items.add(new SampleItem("", "","",""));
		items.add(new SampleItem("Karn�m A�", "Yemek","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		items.add(new SampleItem("Yar�m saat i�inde ��lg�n bir parti ba�l�yor... K�p�k makinesi laz�m!!!", "Parti","15:00","14 km"));
		items.add(new SampleItem("Karn�m A�2", "Yemek","15:30","4 km"));
		
		return items;
	}
	
	public class SampleItem {
		public String title;
		public String category;
		public String time;
		public String distance;
		public SampleItem(String title, String category,String time,String distance) {
			this.title = title; 
			this.category = category;
			this.time = time; 
			this.distance = distance;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {
        
		public SampleAdapter(Context context, int textViewResourceId,ArrayList list) {
			super(context, textViewResourceId,list);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.post_title);
			title.setText(getItem(position).title);
			
			TextView category = (TextView) convertView.findViewById(R.id.category);
			category.setText(getItem(position).category);
			
			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(getItem(position).time);
			
			TextView distance = (TextView) convertView.findViewById(R.id.distance);
			distance.setText(getItem(position).distance);

			if(getItem(position).title != ""){
				ProgressBar bar = (ProgressBar)convertView.findViewById(R.id.post_loading);
				bar.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

	}

	private class NewDataTask extends AsyncTask<Void, Void, SampleItem> {

        @Override
        protected SampleItem doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            
            return new SampleItem("new item", "yemek", "05:30", "9 km");
        }

        @Override
        protected void onPostExecute(SampleItem result) {
        	items.add(0, result);
            // This should be called after refreshing finished
        	postListView.completeRefreshing();

            super.onPostExecute(result);
        }
    }
	
    public class SwipePagerAdapter extends PagerAdapter{

                
        @Override
        public int getCount() {
                return 2;
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
                	View v;
//                	ListView lv = null;
                	if(position == 0){
                		v = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_list_layout, null);
                		
                		postListView = (RefreshableListView)v.findViewById(R.id.post_list_view);
                		postListView.setAdapter(new SampleAdapter(cxt, R.id.post_list_view, getPosts()));
                		postListView.setOnRefreshListener(new OnRefreshListener() {
							
							@Override
							public void onRefresh(RefreshableListView listView) {
								// TODO Auto-generated method stub
								new NewDataTask().execute();
							}
						});
                	}else{
                		v= LayoutInflater.from(getBaseContext()).inflate(R.layout.post_map, null);
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
                        collection.removeView((TextView) view);
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
                	if(pager.getCurrentItem()==0){
                		ImageButton btn = (ImageButton)findViewById(R.id.change_view_btn);
        				btn.setBackgroundResource(R.drawable.map_icon);
        			}else{
        				ImageButton btn = (ImageButton)findViewById(R.id.change_view_btn);
        				btn.setBackgroundResource(R.drawable.menu_icon);
        			}
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
