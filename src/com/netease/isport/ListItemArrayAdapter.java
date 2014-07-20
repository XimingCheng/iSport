package com.netease.isport;

import java.util.ArrayList;

import com.netease.util.RoundImageUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
	 
	public class ListItemArrayAdapter extends ArrayAdapter<ListItem> {
		Context context;
		int layoutResourceId;
		ArrayList<ListItem> items = new ArrayList<ListItem>();
			 
	    public ListItemArrayAdapter(Context context, int layoutResourceId,
	            ArrayList<ListItem> items) {
	        super(context, layoutResourceId, items);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.items = items;
	    }
			 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View v = convertView;
	        ListItemWrapper listItemWrapper = null;
	 
	        if (v == null) {
	            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
	            v = inflater.inflate(layoutResourceId, parent, false);
	            listItemWrapper = new ListItemWrapper();
	            listItemWrapper.mUserName = (TextView) v.findViewById(R.id.user_text);
	            listItemWrapper.mActivityTitile = (TextView) v.findViewById(R.id.list_act_theme);
	            listItemWrapper.mActivityContent = (TextView) v.findViewById(R.id.list_act_main_page);
	            listItemWrapper.mTime = (TextView) v.findViewById(R.id.list_act_time);
	            listItemWrapper.mPeopleCount = (TextView) v.findViewById(R.id.list_act_people_number);
	            listItemWrapper.mUserImage = (ImageView) v.findViewById(R.id.user_list_image);
	           
	            v.setTag(listItemWrapper);
	        } else {
	        	listItemWrapper = (ListItemWrapper) v.getTag();
	        }
	 
	        ListItem item = items.get(position);
	        listItemWrapper.mUserName.setText(item.getmUserName());
	        listItemWrapper.mActivityTitile.setText(item.getmActivityTitile());
	        listItemWrapper.mActivityContent.setText(item.getmActivityContent());
	        listItemWrapper.mTime.setText(item.getmTime());
	        listItemWrapper.mPeopleCount.setText(item.getmPeopleCount());
	        listItemWrapper.mUserImage.setImageBitmap(RoundImageUtil.
	        		toRoundCorner(item.getmUserImage()));
	        listItemWrapper.mUserImage.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                	Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
	            	}
	        });
		 
			return v;
		}
		 
		    static class ListItemWrapper {
	    		TextView mUserName;
		        TextView mActivityTitile;
		        TextView mActivityContent;
		        TextView mTime;
		        TextView mPeopleCount;
		        ImageView mUserImage;    
		   }
 
}
