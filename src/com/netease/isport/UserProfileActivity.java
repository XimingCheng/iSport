package com.netease.isport;

import com.netease.util.GetIntentInstance;
import com.netease.util.RoundImageUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity implements OnClickListener{
	ImageView photoCh;
	
	private static final int REQUEST_CODE = 1;//选择文件的返回码
	private Intent fileChooserIntent;
	
	Intent intent        = GetIntentInstance.getIntent();
	ImageView preStep    = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_profile);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		photoCh=(ImageView)findViewById(R.id.change_photo);
		preStep.setOnClickListener(this);
		photoCh.setOnClickListener(this);
		fileChooserIntent = new Intent(this,fileChooserActivity.class);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		Bitmap output = RoundImageUtil.toRoundCorner(bitmap);
		photoCh.setImageBitmap(output);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.title_bar_menu_btn :{
				UserProfileActivity.this.finish();
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
