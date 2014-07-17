package com.netease.isport;

import com.netease.util.GetIntentInstance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends Activity implements OnClickListener{
	Intent intent=GetIntentInstance.getIntent();
	ImageView preStep=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_profile);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		preStep.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.title_bar_menu_btn :{
				intent.setClass(UserProfileActivity.this, MainActivity.class);
				  startActivity(intent); 
				  break;
			}
		}
		
	}
}
