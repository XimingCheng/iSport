package com.netease.isport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.RoundImageUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;

public class MainActivity extends Activity implements OnClickListener {
	private TextView reflash=null;
	private Bitmap mDefaultBit;
	private SlideMenu mSlideMenu;
	private ProgressDialog progDialog = null;
	private LinearLayout mUserProfileLayout;
	private ImageView mUserImage,cat_basketball,cat_football,cat_pingpang,cat_more,cat_badminton,cat_running;
	private TextView  option_submit_act;
	static final private int LoginId = 1;
	static final private int LogoutId = 2;
	static final private int setImage = 3;
	static final private int useProfile = 4;

	private TextView  option_search_act,option_edit_profile;
	private TextView  option_setting;

	private SharedPreferences sp;
	private ListView mListview;
	private ListItemArrayAdapter mListItemArrayAdapter;
	ArrayList<ListItem> mItemArray = new ArrayList<ListItem>();
	
    protected void onPushed() throws URISyntaxException {
    	if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			reflash.setVisibility(View.VISIBLE);
			return;
		}
    	
    	
    	HttpResponse res = PostandGetConnectionUtil.getConnect(PostandGetConnectionUtil.pushUrl);
		if (PostandGetConnectionUtil.responseCode(res) != 200)
			return;
		Toast.makeText(MainActivity.this, 
				 "onPushed()", Toast.LENGTH_LONG).show();
		String json_str = PostandGetConnectionUtil.GetResponseMessage(res);
		if(json_str.length() != 0) {
			JsonPushRet o = new DecodeJson().jsonPush(json_str);
			mItemArray.clear();
			if(o.getRet().equals("ok")) {
				int count = o.getCount();
				for(int i = 0; i < count; i++) {
					String theme = "主题：" + o.getList().get(i).getTheme();
					String details = "正文：" + o.getList().get(i).getDetails();
					String time = "时间：" + o.getList().get(i).getTime();
					String cnt = "人数："+ o.getList().get(i).getCount();
					String name = o.getList().get(i).getName();
					String img = o.getList().get(i).getImg();
					String id  = o.getList().get(i).getId();
					Bitmap bitmap = mDefaultBit;
					String image_location = PostandGetConnectionUtil.mediaUrlBase + img;
					// get the image from the url
					try{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						URL url_image = new URL(image_location);  
						InputStream is = url_image.openStream();
						bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
						is.close();
					} catch(Exception e) {
			            e.printStackTrace();  
			        }
					dissmissProgressDialog();
					mItemArray.add(new ListItem(name, theme, time, cnt, details, id, bitmap));
				}
			}
		}
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mUserProfileLayout = (LinearLayout) findViewById(R.id.user_image_layout);
		mUserImage = (ImageView) findViewById(R.id.user_image);
		reflash    = (TextView) findViewById(R.id.reflash);
		
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
		cat_more       =(ImageView)findViewById(R.id.cat_more);
		
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		
		try {
			if( !synloginInfo() ) {
				SharedPreferenceUtil.setLogin(false);
				//mSlideMenu.lock();
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mDefaultBit = BitmapFactory.decodeResource(getResources(), R.drawable.user_photo);

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
				 if (SharedPreferenceUtil.isLogin() ) {
//				 Toast.makeText(MainActivity.this, 
//						 "List Item Clicked:" + position + " id " + id, Toast.LENGTH_LONG).show();
					 Intent intent = new Intent();
					 intent.putExtra("id", mItemArray.get((int) id).getmAcTId());
					 intent.putExtra("name", mItemArray.get((int) id).getmUserName());
					 intent.setClass(MainActivity.this, InfoActivity.class);
					 startActivity(intent);
				 } else {
					 Intent intent = new Intent();
					 intent.setClass(MainActivity.this, LoginActivity.class);
				     startActivityForResult(intent,LoginId);
				 }
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
		cat_more.setOnClickListener(this);
		reflash.setOnClickListener(this);
		//timer.schedule(task, 0, 300000);
		//timer.schedule(task, 0, 5000);
		try {
			onPushed();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	boolean synloginInfo() throws URISyntaxException {
		if( SharedPreferenceUtil.isLogin() ) {
			if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
				ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
				return false;
			}
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("other", "y"));
			HttpResponse res = PostandGetConnectionUtil.getConnect(PostandGetConnectionUtil.getinfoUrl, list);
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
						bitmap.compress(CompressFormat.PNG, 100, baos);
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
		   if(v.getId()==R.id.reflash){
			  if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ){
				  ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
				  return;
			  }
			  else{
			     reflash.setVisibility(View.GONE);
				 try {
					runOnUiThread(new Runnable() {
					    public void run() {
					    	mListItemArrayAdapter.notifyDataSetChanged();
					    }
					});
					onPushed();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		   }
		   else if(!SharedPreferenceUtil.isLogin()){
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
						   intent.putExtra("user", "my");
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
					   intent.putExtra("user", "my");
					   intent.setClass(MainActivity.this,UserProfileActivity.class);
					   startActivityForResult(intent, useProfile); 
					   break;
				   }
				   case R.id.option_edit_profile:{
					   intent.setClass(MainActivity.this,EditProfileActivity.class);
					   startActivityForResult(intent, setImage); 
					   break;
				   }
				   case R.id.option_settings: {
					   intent.setClass(MainActivity.this,SettingActivity.class);
					   startActivityForResult(intent, LogoutId);
					   break;
				   }
				   case R.id.cat_basketball:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   intent.putExtra("class_act", "篮球");
					   intent.putExtra("flag",0);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_football:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   intent.putExtra("class_act", "足球");
					   intent.putExtra("flag",0);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_pingpang:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   intent.putExtra("class_act", "乒乓球");
					   intent.putExtra("flag",0);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_badminton:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   intent.putExtra("class_act", "羽毛球");
					   intent.putExtra("flag",0);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_running:{
					   intent.setClass(MainActivity.this,ResultListActivity.class);
					   intent.putExtra("class_act", "跑步");
					   intent.putExtra("flag",0);
					   startActivity(intent); 
					   break;
				   }
				   case R.id.cat_more:{
					   intent.setClass(MainActivity.this,SearchActivity.class);
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
				//mSlideMenu.unlock();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			refresh_ctrl();
		} else if (LogoutId == requestCode && RESULT_OK == resultCode) {
			//mSlideMenu.lock();
			mUserImage.setImageBitmap(mDefaultBit);
			TextView nametx = (TextView) findViewById(R.id.user_name);
			nametx.setText("没有登录的用户");
		} else if (LogoutId == requestCode && RESULT_CANCELED == resultCode) {
			MainActivity.this.finish();
		} 
		else if(setImage == requestCode && RESULT_OK == resultCode) {
			String imageBase64 = sp.getString("imageBase64", "");
			byte[] base64Bytes = Base64.decode(imageBase64.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			Bitmap bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(bais));
			mUserImage.setImageBitmap(bitmap);
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        PackageManager pm = getPackageManager();  
        ResolveInfo homeInfo = 
            pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0); 
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	if( SharedPreferenceUtil.isLogin() ) {
	            ActivityInfo ai = homeInfo.activityInfo;  
	            Intent startIntent = new Intent(Intent.ACTION_MAIN);  
	            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
	            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));  
	            startActivitySafely(startIntent);  
	            return true;
        	} else
        		return super.onKeyDown(keyCode, event);
        } else  
            return super.onKeyDown(keyCode, event);  
    }
    private void startActivitySafely(Intent intent) {  
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        try {  
            startActivity(intent);  
        } catch (ActivityNotFoundException e) {  
            Toast.makeText(this, "null",  
                    Toast.LENGTH_SHORT).show();  
        } catch (SecurityException e) {  
            Toast.makeText(this, "null",  
                    Toast.LENGTH_SHORT).show();   
        }  
    }
    private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(MainActivity.this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在刷新...");
		progDialog.show();
	}
    private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
}
