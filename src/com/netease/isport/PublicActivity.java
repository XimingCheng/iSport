package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PublicActivity extends Activity implements OnClickListener{
    Button button=null;
    ImageView fanhui=null;
    Intent intent=new Intent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_submit_act);
		button=(Button)findViewById(R.id.submit_act);
		fanhui=(ImageView)findViewById(R.id.title_bar_menu_btn);
		button.setOnClickListener(this);
		fanhui.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubb
		switch(v.getId()) {
			case R.id.submit_act :{
				//ÒµÎñÂß¼­
				break;
			}
			case R.id.title_bar_menu_btn : {
				intent.setClass(PublicActivity.this,MainActivity.class);
				startActivity(intent);
			}
			
		}
		
	}
}
