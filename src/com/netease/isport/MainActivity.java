package com.netease.isport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.RoundImageUtil;
import com.netease.util.SharedPreferenceUtil;

public class MainActivity extends Activity implements OnClickListener {
	private Bitmap mDefaultBit;
	private SlideMenu mSlideMenu;
	private LinearLayout mUserProfileLayout;
	private ImageView mUserImage,cat_basketball,cat_football,cat_pingpang,cat_badminton,cat_running;
	private TextView  option_submit_act;
	static final private int LoginId = 1;
	static final private int LogoutId = 2;

	private TextView  option_search_act,option_edit_profile;
	private TextView  option_setting;

	private SharedPreferences sp;
	private ListView mListview;
	private ListItemArrayAdapter mListItemArrayAdapter;
	ArrayList<ListItem> mItemArray = new ArrayList<ListItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mUserProfileLayout = (LinearLayout) findViewById(R.id.user_image_layout);
		mUserImage = (ImageView) findViewById(R.id.user_image);

		SharedPreferenceUtil.setSharedPreferences(sp);

		option_submit_act=(TextView)findViewById(R.id.option_submit_act);
		option_search_act   = (TextView) findViewById(R.id.option_search_act);
		option_edit_profile=(TextView) findViewById(R.id.option_edit_profile);
		option_setting = (TextView) findViewById(R.id.option_settings);
		cat_basketball = (ImageView)findViewById(R.id.cat_basketball);
		cat_football   =(ImageView)findViewById(R.id.cat_football);
		cat_pingpang   =(ImageView)findViewById(R.id.cat_pingpang);
		cat_badminton  =(ImageView)findViewById(R.id.cat_badminton);
		cat_running    =(ImageView)findViewById(R.id.cat_running);
		
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		
		try {
			if( !synloginInfo() ) {
				SharedPreferenceUtil.setLogin(false);
				mSlideMenu.lock();
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mDefaultBit = BitmapFactory.decodeResource(getResources(), R.drawable.user_photo);
		
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", mDefaultBit));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数：3/20", "正文：测试的字符串", mDefaultBit));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", mDefaultBit));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", mDefaultBit));
		mItemArray.add(new ListItem("高圆圆", "主题：打篮球", 
				"时间：2014/07/24 8:30 - 11:30", "人数 ：3/20", "正文：测试的字符串", mDefaultBit));

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
						 "List Item Clicked:" + position + " id " + id, Toast.LENGTH_LONG).show();
				 Intent intent = new Intent();
				 intent.setClass(MainActivity.this, InfoActivity.class);
				 startActivity(intent);
			 }
		});

		//Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		mUserImage.setImageBitmap(mDefaultBit);
		refresh_ctrl();
		menuImg.setOnClickListener(this);
		option_edit_profile.setOnClickListener(this);
		option_submit_act.setOnClickListener(this);
		option_search_act.setOnClickListener(this);
		cat_basketball.setOnClickListener(this);
		cat_football.setOnClickListener(this);
		cat_badminton.setOnClickListener(this);
		cat_pingpang.setOnClickListener(this);
		cat_running.setOnClickListener(this);
		option_setting.setOnClickListener(this);
		mUserProfileLayout.setOnClickListener(this);
		mUserImage.setOnClickListener(this);
	}
	
	boolean synloginInfo() throws URISyntaxException {
		if( SharedPreferenceUtil.isLogin() ) {
			HttpResponse res = PostandGetConnectionUtil.getConnect(PostandGetConnectionUtil.getinfoUrl);
			if (PostandGetConnectionUtil.responseCode(res) != 200)
				return false;
			String json_str = PostandGetConnectionUtil.GetResponseMessage(res);
			if(json_str.length() != 0) {
				JsonInfoResult o = new DecodeJson().jsonInfo(json_str);
				if(o.getRet().equals("ok")) {
					String image_location = PostandGetConnectionUtil.mediaUrlBase + o.getUserimage();
					String imageBase64 = "";
					// get the image from the url
					try{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						URL url_image = new URL(image_location);  
						InputStream is = url_image.openStream();  
						Bitmap bitmap  = BitmapFactory.decodeStream(is);
						bitmap.compress(CompressFormat.JPEG, 100, baos);
						imageBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
						is.close();
					} catch(Exception e) {
			            e.printStackTrace();  
			        } 
					SharedPreferenceUtil.saveAccount(o.getUsername(), o.getLocation(),
							o.getScore(), o.getCompleted(), o.getUncompleted(),
							o.getSex(), imageBase64, o.getLabel());
					return true;
				} else
					return false;
			} else
				return false;
		} else {
			return false;
		}
	}
	
	private void refresh_ctrl() {
		if( SharedPreferenceUtil.isLogin() ) {
			String username = sp.getString("username", "");
			String imageBase64 = sp.getString("imageBase64", "");
			if(username.length() != 0 || imageBase64.length() != 0) {
				TextView nametx = (TextView) findViewById(R.id.user_name);
				nametx.setText(username);
				byte[] base64Bytes = Base64.decode(imageBase64.getBytes(), Base64.DEFAULT);
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				Bitmap bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(bais));
				mUserImage.setImageBitmap(bitmap);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		   Intent intent = new Intent();
		   if(!SharedPreferenceUtil.isLogin()){
		    	intent.setClass(MainActivity.this, LoginActivity.class);
		    	startActivityForResult(intent,LoginId);
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
						   startActivity(intent);
						   mSlideMenu.closeMenu();
						   break;
					   }
				   }
				   case R.id.title_bar_menu_btn:{
//					   intent.setClass(MainActivity.this,LoginActivity.class);
//					   startActivity(intent);
					   if(!mSlideMenu.isMainScreenShowing()) {
						   mSlideMenu.closeMenu();
					   } else {
						   mSlideMenu.openMenu();
					   }
					   break;
				   }
				   case R.id.user_image:{
					   intent.setClass(MainActivity.this,UserProfileActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.option_edit_profile:{
					   intent.setClass(MainActivity.this,EditProfileActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.option_settings: {
					   intent.setClass(MainActivity.this,SettingActivity.class);
					   startActivityForResult(intent, LogoutId);
					   break;
				   }
				   case R.id.cat_basketball:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_football:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_pingpang:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_badminton:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_running:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
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
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(LoginId == requestCode && RESULT_OK == resultCode) {
			try {
				synloginInfo();
				mSlideMenu.unlock();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refresh_ctrl();
		} else if (LogoutId == requestCode && RESULT_OK == resultCode) {
			mSlideMenu.lock();
			mUserImage.setImageBitmap(mDefaultBit);
			TextView nametx = (TextView) findViewById(R.id.user_name);
			nametx.setText("蒹葭苍苍 白露为霜");
		}
	}
}
