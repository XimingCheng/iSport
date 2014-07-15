package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	TextView textView=null;
	ImageView preStep=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		textView=(TextView)findViewById(R.id.register);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);	
		preStep.setOnClickListener(this);
		textView.setOnClickListener(this);
		SharedPreferences sp=getSharedPreferences("test", 0);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		if(v.getId()==R.id.register){	  
		    intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
		 }
		else if(v.getId()==R.id.title_bar_menu_btn){
		    intent.setClass(LoginActivity.this, MainActivity.class);
		    startActivity(intent);
		}
		
	}
}
