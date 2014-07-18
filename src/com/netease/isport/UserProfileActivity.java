package com.netease.isport;

import com.netease.util.GetIntentInstance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity implements OnClickListener{
	Intent intent=GetIntentInstance.getIntent();
	ImageView preStep=null,photoCh;
	private static final int REQUEST_CODE = 1;//选择文件的返回码
	private Intent fileChooserIntent ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_profile);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		photoCh=(ImageView)findViewById(R.id.change_photo);
		preStep.setOnClickListener(this);
		photoCh.setOnClickListener(this);
		fileChooserIntent = new Intent(this,fileChooserActivity.class);
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
			case R.id.change_photo:
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				    startActivityForResult(fileChooserIntent , REQUEST_CODE);
		    	else
		    		toast(getText(R.string.sdcard_unmonted_hint));
				break;
		}
		
	}
	private void toast(CharSequence hint){
	    Toast.makeText(this, hint , Toast.LENGTH_SHORT).show();
	}
}
