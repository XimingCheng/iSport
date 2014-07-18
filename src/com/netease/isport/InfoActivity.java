package com.netease.isport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class InfoActivity extends Activity implements OnClickListener {

	private ImageView mBackBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
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
