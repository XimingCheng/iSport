package com.netease.isport;

import com.netease.util.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener{
	Button button=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		button=(Button)findViewById(R.id.complete);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.register){
			  Intent intent=new Intent();
			  intent.setClass(RegisterActivity.this, MainActivity.class);
			  startActivity(intent);
			}
	}
}