package com.netease.isport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.GetIntentInstance;
import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.RoundImageUtil;
import com.netease.util.ToastUtil;

public class InfoActivity extends Activity implements OnClickListener {

	private ImageView mBackBtn;
	private ImageView mInfoProtrait;
	private ImageView mInfoProtrait1;
	private ImageView mInfoProtrait2;
	private ImageView mInfoProtrait3,mapPic;
	private TextView  adressInfo,mapInfo,addressInfo;
	private TextView  mTxTheme, mTxTime, mTxDetails, mTxJoinedNum;
	private String    mapString;
	private String    mActId;
	private Button   join;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		mTxTheme       = (TextView) findViewById(R.id.activity_theme);
		mTxTime        = (TextView) findViewById(R.id.activity_info_time_range);
		mTxDetails     = (TextView) findViewById(R.id.activity_info_details);
		mTxJoinedNum   = (TextView) findViewById(R.id.activity_participate_num);
		mInfoProtrait  = (ImageView) findViewById(R.id.activity_info_protrait);
		join           = (Button)    findViewById(R.id.join);
		
		mInfoProtrait.setOnClickListener(new OnClickListener() {
			@Override
            public void onClick(View v) {
					Intent intent1 = getIntent();
					String user_name = intent1.getStringExtra("name");
                	//Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_LONG).show();
            		Intent intent = new Intent();
            		intent.putExtra("user", "other");
            		intent.putExtra("name", user_name);
            		intent.setClass(getApplicationContext(), UserProfileActivity.class);
            		startActivity(intent);
            	}
		});
		mInfoProtrait1 = (ImageView) findViewById(R.id.activity_info_protrait_001);
		mInfoProtrait2 = (ImageView) findViewById(R.id.activity_info_protrait_002);
		mInfoProtrait3 = (ImageView) findViewById(R.id.activity_info_protrait_003);
		mapInfo        = (TextView)  findViewById(R.id.mapInfo);
		addressInfo    = (TextView)  findViewById(R.id.addressInfo);
		mapPic = (ImageView) findViewById(R.id.mapPic);
		adressInfo=(TextView)findViewById(R.id.activity_info_map_link);
		mapString =(String) adressInfo.getText();
		/*adressInfo.setText(Html.fromHtml("<u>" + adressInfo.getText() + "</u>" ));*/
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		mInfoProtrait.setImageBitmap(output);
		mInfoProtrait1.setImageBitmap(output);
		mInfoProtrait2.setImageBitmap(output);
		mInfoProtrait3.setImageBitmap(output);
		mapPic.setOnClickListener(this);
		mapInfo.setOnClickListener(this);
		mBackBtn = (ImageView) findViewById(R.id.title_bar_menu_btn);
		mBackBtn.setOnClickListener(this);
		addressInfo.setOnClickListener(this);
		adressInfo.setOnClickListener(this);
		join.setOnClickListener(this);
		Intent intent1 = getIntent();
		mActId = intent1.getStringExtra("id");
		try {
			setContent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setContent() throws URISyntaxException {
		if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			return;
		}
		HttpResponse httpResponse=null;
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("id", mActId));
		httpResponse = PostandGetConnectionUtil.getConnect(PostandGetConnectionUtil.getActInfoUrl,list);
		if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse)== 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonActRet o = new DecodeJson().jsonActRet(message);
            if(o.getRet().equals("ok")) {
                if(o.getBsubmit().equals("yes")) {
                	join.setText("完 成");
                } else if(o.getBjoined().equals("yes")) {
                	join.setText("退 出");
                } else
                	join.setText("加 入");
                if(o.getBtimeout().equals("y")) {
                	join.setText("已 完");
                }
            	mTxTheme.setText(o.getTheme_act());
            	mTxTime.setText("时间：" + o.getTime_act());
            	adressInfo.setText(o.getLocation_act());
            	mapString = o.getLocation_act();
            	mTxDetails.setText(o.getDetail_act());
            	mTxJoinedNum.setText("参加人数 " + o.getJoined_num() + "/" + o.getTotalnum());
            	try {
					setAllImage(o.getSubmit_img(), o.getImg1(), o.getImg2(), o.getImg3());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	//ToastUtil.show(getApplicationContext(), "info成功");
            } else {
            	ToastUtil.show(getApplicationContext(), "info失败");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
	}
	
	void setAllImage(String mainimage, String img1, String img2, String img3) throws IOException {
		if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			return;
		}
		String image_location = PostandGetConnectionUtil.mediaUrlBase + mainimage;
		URL url_image = new URL(image_location);  
		InputStream is = url_image.openStream();  
		Bitmap bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
		mInfoProtrait.setImageBitmap(bitmap);
		if(img1.length() > 0) {
			image_location = PostandGetConnectionUtil.mediaUrlBase + img1;
			url_image = new URL(image_location);  
			is = url_image.openStream();
			bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
			mInfoProtrait1.setImageBitmap(bitmap);
		} else 
			mInfoProtrait1.setVisibility(View.GONE);
		if (img2.length() > 0) {
			image_location = PostandGetConnectionUtil.mediaUrlBase + img2;
			url_image = new URL(image_location);  
			is = url_image.openStream();
			bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
			mInfoProtrait2.setImageBitmap(bitmap);
		} else 
			mInfoProtrait2.setVisibility(View.GONE);
		if (img3.length() > 0) {
			image_location = PostandGetConnectionUtil.mediaUrlBase + img3;
			url_image = new URL(image_location);  
			is = url_image.openStream();
			bitmap.recycle();
			bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
			mInfoProtrait3.setImageBitmap(bitmap);
		} else 
			mInfoProtrait3.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.title_bar_menu_btn:{
			InfoActivity.this.finish();
			break;
		}
		case R.id.addressInfo:{
			Intent intent=GetIntentInstance.getIntent();
			intent.setClass(InfoActivity.this, PoiKeywordSearchActivity.class);
			intent.putExtra("position", mapString);
			startActivity(intent);
			break;
		}
		case R.id.mapInfo:{
			//String map=(String) adressInfo.getText();
			Intent intent=GetIntentInstance.getIntent();
			intent.setClass(InfoActivity.this, PoiKeywordSearchActivity.class);
			intent.putExtra("position", mapString);
			startActivity(intent);
			break;
		}
		case R.id.mapPic:{
			//String map=(String) adressInfo.getText();
			Intent intent=GetIntentInstance.getIntent();
			intent.setClass(InfoActivity.this, PoiKeywordSearchActivity.class);
			intent.putExtra("position", mapString);
			startActivity(intent);
			break;
		}
		case R.id.activity_info_map_link:{
			Intent intent=GetIntentInstance.getIntent();
			intent.setClass(InfoActivity.this, PoiKeywordSearchActivity.class);
			intent.putExtra("position", mapString);
			startActivity(intent);
			break;
		}
		case R.id.join:{
			//Intent intent=GetIntentInstance.getIntent();
			//ToastUtil.show(getApplicationContext(), "点击");
			String tx = (String)join.getText();
			if (tx.equals("加 入")) {
				join();
			} else if (tx.equals("退 出")){
				unjoin();
			} else if (tx.equals("完 成")) {
				com_act();
			} else {
				ToastUtil.show(getApplicationContext(), "活动已完成！");
			}
		}
		
	}
	}
	
	private void com_act() {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("id_act",mActId));
		HttpResponse httpResponse=null;
		PostandGetConnectionUtil.setParm(list);
		try {
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.comUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(httpResponse!=null&&PostandGetConnectionUtil.responseCode(httpResponse)== 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            
            if(o.getRet().equals("ok")) {
            	ToastUtil.show(getApplicationContext(), "完成成功！");
            	InfoActivity.this.finish();
            } else {
            	ToastUtil.show(getApplicationContext(), "完成失败");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
	}
	
	private void unjoin() {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("id_act",mActId));
		HttpResponse httpResponse=null;
		PostandGetConnectionUtil.setParm(list);
		try {
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.unjoinUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(httpResponse!=null&&PostandGetConnectionUtil.responseCode(httpResponse)== 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            
            if(o.getRet().equals("ok")) {
            	ToastUtil.show(getApplicationContext(), "退出成功！");
            	InfoActivity.this.finish();
            } else {
            	ToastUtil.show(getApplicationContext(), "退出失败");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
	}
	
	private void join(){
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("id_act",mActId));
		HttpResponse httpResponse=null;
		PostandGetConnectionUtil.setParm(list);
		try {
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.joinUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(httpResponse!=null&&PostandGetConnectionUtil.responseCode(httpResponse)== 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            
            if(o.getRet().equals("ok")) {
            	ToastUtil.show(getApplicationContext(), "加入成功！");
            	InfoActivity.this.finish();
            } else {
            	ToastUtil.show(getApplicationContext(), "加入失败");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}	
	}
}
