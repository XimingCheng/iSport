package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterActivity extends Activity implements OnClickListener{
	Button button=null;
	ImageView preStep=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		button=(Button)findViewById(R.id.complete);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		button.setOnClickListener(this);
		preStep.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		if(v.getId()==R.id.register){			  
		    intent.setClass(RegisterActivity.this, MainActivity.class);
		    startActivity(intent);
		   }
		else if(v.getId()==R.id.title_bar_menu_btn){
		    intent.setClass(RegisterActivity.this, LoginActivity.class);
		    startActivity(intent);
		}
	}
}