package com.netease.isport;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.RoundImageUtil;
import com.netease.util.SharedPreferenceUtil;

public class MainActivity extends Activity  implements OnClickListener {
	private SlideMenu mSlideMenu;
	private LinearLayout mUserProfileLayout;
	private ImageView mUserImage;
	private TextView  option_submit_act;
	private TextView  option_search_act;
	private TextView  option_setting;

	private SharedPreferences sp;
	private ListView mListview;
	private ListItemArrayAdapter mListItemArrayAdapter;
	ArrayList<ListItem> mItemArray = new ArrayList<ListItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    sp = getSharedPreferences("setting", 0);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mUserProfileLayout = (LinearLayout) findViewById(R.id.user_image_layout);
		mUserImage = (ImageView) findViewById(R.id.user_image);
		//userimg = (ImageView) findViewById(R.id.user_image);
		
		option_submit_act=(TextView)findViewById(R.id.option_submit_act);
		option_search_act   = (TextView) findViewById(R.id.option_search_act);
		
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
		
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", bitmap));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", bitmap));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串",bitmap));

		// set the array adapter to use the above array list and tell the listview to set as the adapter
	    // our custom adapter
		mListItemArrayAdapter = new ListItemArrayAdapter(MainActivity.this, R.layout.list_item, mItemArray);
		mListview= (ListView) findViewById(R.id.pushed_list);
		mListview.setItemsCanFocus(false);
		mListview.setAdapter(mListItemArrayAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> parent, View v,
			     final int position, long id) {
			 
				 Toast.makeText(MainActivity.this, 
						 "List Item Clicked:" + position, Toast.LENGTH_LONG).show();
			 }
		});

		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		mUserImage.setImageBitmap(output);
		menuImg.setOnClickListener(this);
		
		option_submit_act.setOnClickListener(this);
		option_search_act.setOnClickListener(this);
		mUserProfileLayout.setOnClickListener(this);
		mUserImage.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		   Intent intent = new Intent();
		   if(!sp.getAll().isEmpty()||(!SharedPreferenceUtil.getAccount())){
		    	
		    	intent.setClass(MainActivity.this, LoginActivity.class);
		    	startActivity(intent);
		   } else {
			   switch(v.getId()) {
				   case R.id.slide_menu:{
					   if(mSlideMenu.isMainScreenShowing()){
						   mSlideMenu.openMenu(); break;
					   } else {
						   mSlideMenu.closeMenu(); break;
					   }
				   }
				   case R.id.option_submit_act :{
					   if (!mSlideMenu.isMainScreenShowing()) {
						   intent.setClass(MainActivity.this,PublicActivity.class);
						   startActivity(intent);
						   break;
					   }
				   }
				   case R.id.option_search_act :{
					   if (!mSlideMenu.isMainScreenShowing()) {
						   intent.setClass(MainActivity.this,SearchActivity.class);
						   startActivity(intent);
						   break;
					   }
				   }
				   case R.id.user_image_layout:{
					   if (!mSlideMenu.isMainScreenShowing()) {
						   intent.setClass(MainActivity.this, UserProfileActivity.class);
						   mSlideMenu.closeMenu();
						   break;
					   }
				   }
				   case R.id.title_bar_menu_btn:{
					   intent.setClass(MainActivity.this,LoginActivity.class);
					   startActivity(intent);
					   break;
				   }
				   case R.id.user_image:{
					   intent.setClass(MainActivity.this,UserProfileActivity.class);
					   startActivity(intent);
					   break;
				   }
			   } 
		   }
	}
	
	    @Override  
	    protected void onStop() {  
	        super.onStop();  
	        mSlideMenu.closeMenu();
	    } 
	
}
