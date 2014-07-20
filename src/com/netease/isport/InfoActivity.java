package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.netease.util.GetIntentInstance;
import com.netease.util.RoundImageUtil;

public class InfoActivity extends Activity implements OnClickListener {

	private ImageView mBackBtn;
	private ImageView mInfoProtrait;
	private ImageView mInfoProtrait1;
	private ImageView mInfoProtrait2;
	private ImageView mInfoProtrait3,mapPic;
	private TextView  adressInfo,mapInfo,addressInfo;
	private String    mapString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		mInfoProtrait  = (ImageView) findViewById(R.id.activity_info_protrait);
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
		
	}
	}
}
