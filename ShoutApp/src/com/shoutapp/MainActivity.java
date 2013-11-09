package com.shoutapp;

import java.util.ArrayList;

import com.shoutapp.SlidingMenuBaseActivity.MenuListFragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity{

	private Context cxt;
	ViewPager pager;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		cxt = this;
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
		View swipe = LayoutInflater.from(getBaseContext()).inflate(R.layout.swipe, null);
		RelativeLayout swipeLayout = (RelativeLayout)swipe.findViewById(R.id.swipe_layout);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
											      RelativeLayout.LayoutParams.WRAP_CONTENT,
											      RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW,R.id.topBar);
        swipeLayout.setLayoutParams(lp);
        mainLayout.addView(swipe);
        SwipePagerAdapter adapter = new SwipePagerAdapter();
        pager = (ViewPager)swipe.findViewById(R.id.page_swiper);
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
			String a = getItem(position).title;
			title.setText(getItem(position).title);
			
			TextView category = (TextView) convertView.findViewById(R.id.category);
			String b = getItem(position).category;
			category.setText(getItem(position).category);
			
			TextView time = (TextView) convertView.findViewById(R.id.time);
			String c = getItem(position).time;
			time.setText(getItem(position).time);
			
			TextView distance = (TextView) convertView.findViewById(R.id.distance);
			String d = getItem(position).distance;
			distance.setText(getItem(position).distance);

			return convertView;
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
                	if(position == 0){
                		v = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_list_layout, null);
                		
                		ListView lv = (ListView)v.findViewById(R.id.post_list_view);
                		ArrayList<SampleItem> items = new ArrayList<MainActivity.SampleItem>();
                		items.add(new SampleItem("Karným Aç", "Yemek","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		items.add(new SampleItem("Yarým saat içinde çýlgýn bir parti baþlýyor... Köpük makinesi lazým!!!", "Parti","15:00","14 km"));
                		items.add(new SampleItem("Karným Aç2", "Yemek","15:30","4 km"));
                		lv.setAdapter(new SampleAdapter(cxt, R.id.post_list_view, items));
                		
                	}else{
                		v= LayoutInflater.from(getBaseContext()).inflate(R.layout.post_map, null);
                	}
                	
                	
//                        TextView tv = new TextView(cxt);
//                        tv.setText(getString(R.string.app_message) + position);
//                        tv.setTextColor(Color.WHITE);
//                        tv.setTextSize(30);
                        
                        
                        
                        
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
                public void finishUpdate(ViewGroup arg0) {}
                

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
