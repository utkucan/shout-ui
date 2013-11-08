package com.shoutapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoutapp.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class SlidingMenuBaseActivity extends SlidingFragmentActivity   {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setBehindContentView(R.layout.menu_frame);
		ListFragment mFrag;
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new MenuListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
		
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}
	
	public static class MenuListFragment extends ListFragment{
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.sliding_menu_list, null);
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			SampleAdapter adapter = new SampleAdapter(getActivity());
			
			adapter.add(new SampleItem("Help", R.drawable.ipod_icon_unknown));
			adapter.add(new SampleItem("Share", R.drawable.android_share_icon));
			adapter.add(new SampleItem("About", R.drawable.info_icon));
			adapter.add(new SampleItem("Account", R.drawable.login_icon));
			adapter.add(new SampleItem("Notifications", R.drawable.thread_announcement));
			setListAdapter(adapter);
		}

		private class SampleItem {
			public String tag;
			public int iconRes;
			public SampleItem(String tag, int iconRes) {
				this.tag = tag; 
				this.iconRes = iconRes;
			}
		}

		public class SampleAdapter extends ArrayAdapter<SampleItem> {

			public SampleAdapter(Context context) {
				super(context, 0);
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.side_menu_row, null);
				}
				ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
				icon.setImageResource(getItem(position).iconRes);
				TextView title = (TextView) convertView.findViewById(R.id.row_title);
				title.setText(getItem(position).tag);

				return convertView;
			}

		}
	}

}
