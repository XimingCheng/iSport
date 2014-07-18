package com.netease.isport;

import java.util.ArrayList;
import com.netease.util.GetIntentInstance;
import com.netease.util.RoundImageUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity 
	implements OnViewChangeListener, OnClickListener{
	ImageView photoCh;
	
	private static final int REQUEST_CODE = 1;//选择文件的返回码
	private Intent fileChooserIntent;
	
	Intent intent        = GetIntentInstance.getIntent();
	ImageView preStep    = null;
	
	private LinearLayout[] mImageViews;
	private int mViewCount;
	private int mCurSel;
	private TextView mCompletedTextView;
	private TextView mUnCompletedTextView;
	private ListScrollLayout mScrollLayout;
	private ListView  mCompletedListView;
	private ListView  mUnCompletedListView;
	private ListItemArrayAdapter mCompletedListAdapter;
	private ListItemArrayAdapter mUnCompletedListAdapter;
	ArrayList<ListItem> mCompletedItemArray   = new ArrayList<ListItem>();
	ArrayList<ListItem> mUnCompletedItemArray = new ArrayList<ListItem>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_profile);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		photoCh=(ImageView)findViewById(R.id.change_photo);
		preStep.setOnClickListener(this);
		photoCh.setOnClickListener(this);
		fileChooserIntent = new Intent(this,fileChooserActivity.class);
		mCompletedTextView   = (TextView) findViewById(R.id.compeleted_text);
		mUnCompletedTextView = (TextView) findViewById(R.id.uncompeleted_text);
		mScrollLayout = (ListScrollLayout) findViewById(R.id.listScrollLayout);
		mViewCount = mScrollLayout.getChildCount();
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lllayout);
    	mImageViews = new LinearLayout[mViewCount];   	
    	for(int i = 0; i < mViewCount; i++)    	{
    		mImageViews[i] = (LinearLayout) linearLayout.getChildAt(i);
    		mImageViews[i].setEnabled(true);
    		mImageViews[i].setOnClickListener(this);
    		mImageViews[i].setTag(i);
    	}
    	mCurSel = 0;
    	mImageViews[mCurSel].setEnabled(false);    	
    	mScrollLayout.SetOnViewChangeListener(this);
    	
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		photoCh.setImageBitmap(output);
		
		mCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", output));
		mCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", output));
		mCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		mCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		mCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		
		mUnCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", output));
		mUnCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", output));
		mUnCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		mUnCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		mUnCompletedItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", output));
		
		mCompletedListAdapter = new ListItemArrayAdapter(UserProfileActivity.this,
				R.layout.list_item, mCompletedItemArray);
		mUnCompletedListAdapter = new ListItemArrayAdapter(UserProfileActivity.this,
				R.layout.list_item, mUnCompletedItemArray);
		mCompletedListView   = (ListView) findViewById(R.id.completed_list);
		mUnCompletedListView = (ListView) findViewById(R.id.uncompleted_list);
		mCompletedListView.setItemsCanFocus(false);
		mUnCompletedListView.setItemsCanFocus(false);
		mCompletedListView.setAdapter(mCompletedListAdapter);
		mUnCompletedListView.setAdapter(mUnCompletedListAdapter);
	}
	
	private void setCurPoint(int index)
    {
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    	
    	if(index == 0){
    		mUnCompletedTextView.setTextColor(0xff228B22);
    		mCompletedTextView.setTextColor(Color.BLACK);
    	} else {
    		mUnCompletedTextView.setTextColor(Color.BLACK);
    		mCompletedTextView.setTextColor(0xff228B22);
    	}
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.title_bar_menu_btn :{
				UserProfileActivity.this.finish();
				break;
			}
			case R.id.change_photo:
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				    startActivityForResult(fileChooserIntent , REQUEST_CODE);
		    	else
		    		toast(getText(R.string.sdcard_unmonted_hint));
				break;
		}
		
	}
	private void toast(CharSequence hint){
	    Toast.makeText(this, hint , Toast.LENGTH_SHORT).show();
	}

	@Override
	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
		setCurPoint(view);
	}
}
