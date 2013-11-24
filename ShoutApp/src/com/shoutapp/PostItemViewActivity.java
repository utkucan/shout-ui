package com.shoutapp;

import java.util.ArrayList;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PostItemViewActivity extends BaseActivity{

//	ListView comments;
	LinearLayout comment_list_lay;
	ScrollView scrollv;
	RelativeLayout mapLay;
	boolean isMapLayHeightSet = false;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

		View layout = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_item_preview, null);
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
        
        ArrayList<CommentItemObjet> comment_list = Model.getComments();

        for(int i = 0; i< comment_list.size(); i++){
        	addCommentPreview(comment_list.get(i));
        }
        
//        timerHandler.postDelayed(timerRunnable, 3000);
	}
	
	private void addCommentPreview(CommentItemObjet object){
		View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);
		TextView comment = (TextView) comment_item.findViewById(R.id.comment_text);
		comment.setText(object.comment);
		
		TextView owner = (TextView) comment_item.findViewById(R.id.comment_owner);
		owner.setText(object.owner);
		comment_list_lay.addView(comment_item);
	}
	
//	public class CommentItemObjet{
//		public String comment;
//		public String owner;
//		public CommentItemObjet(String comment, String owner) {
//			this.comment = comment; 
//			this.owner = owner;
//		}
//	}
	
	
	
//	int comment_count = 0;
//	Handler timerHandler = new Handler();
//    Runnable timerRunnable = new Runnable() {
//
//        @Override
//        public void run() {
//        	comment_count++;
//        	View comment_item = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item, null);
//			TextView comment = (TextView) comment_item.findViewById(R.id.comment_text);
//			comment.setText(""+comment_count);
//			
//			TextView owner = (TextView) comment_item.findViewById(R.id.comment_owner);
//			owner.setText("sercan");
//			comment_list_lay.addView(comment_item);
//			timerHandler.postDelayed(this, 3000);
//        }
//    };
	
}
