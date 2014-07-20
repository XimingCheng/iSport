package com.netease.isport;

import com.netease.util.RoundImageUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class InfoActivity extends Activity implements OnClickListener {

	private ImageView mBackBtn;
	private ImageView mInfoProtrait;
	private ImageView mInfoProtrait1;
	private ImageView mInfoProtrait2;
	private ImageView mInfoProtrait3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		mInfoProtrait  = (ImageView) findViewById(R.id.activity_info_protrait);
		mInfoProtrait1 = (ImageView) findViewById(R.id.activity_info_protrait_001);
		mInfoProtrait2 = (ImageView) findViewById(R.id.activity_info_protrait_002);
		mInfoProtrait3 = (ImageView) findViewById(R.id.activity_info_protrait_003);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		mInfoProtrait.setImageBitmap(output);
		mInfoProtrait1.setImageBitmap(output);
		mInfoProtrait2.setImageBitmap(output);
		mInfoProtrait3.setImageBitmap(output);
		
		mBackBtn = (ImageView) findViewById(R.id.title_bar_menu_btn);
		mBackBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.title_bar_menu_btn:
			InfoActivity.this.finish();
			break;
		}
	}
}
