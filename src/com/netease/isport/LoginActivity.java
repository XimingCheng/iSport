package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	TextView textView=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		textView=(TextView)findViewById(R.id.register);
		textView.setOnClickListener(this);
		SharedPreferences sp=getSharedPreferences("test", 0);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.register){
			  Intent intent=new Intent();
			  intent.setClass(LoginActivity.this, RegisterActivity.class);
			  startActivity(intent);
			}
		
	}
}
