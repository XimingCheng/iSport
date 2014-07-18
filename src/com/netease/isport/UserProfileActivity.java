package com.netease.isport;

import com.netease.util.GetIntentInstance;
import com.netease.util.RoundImageUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements OnClickListener{
	
	Intent intent        = GetIntentInstance.getIntent();
	ImageView preStep    = null;
	ImageView mUserImage = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_profile);
		
		preStep = (ImageView)findViewById(R.id.title_bar_menu_btn);
		preStep.setOnClickListener(this);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		mUserImage = (ImageView) findViewById(R.id.profile_user_image);
		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		mUserImage.setImageBitmap(output);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.title_bar_menu_btn :{
				UserProfileActivity.this.finish();
				break;
			}
		}
		
	}
}
