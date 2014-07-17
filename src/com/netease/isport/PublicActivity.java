package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.netease.util.GetIntentInstance;

public class PublicActivity extends Activity implements OnClickListener{
    Button button=null;
    ImageView fanhui=null;
    TextView activityTheme=null;
    Spinner spinner=null;
    TextView userCount,act_location,act_content=null;
    Intent intent=GetIntentInstance.getIntent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_submit_act);
		
		button=(Button)findViewById(R.id.submit_act);
		fanhui=(ImageView)findViewById(R.id.title_bar_menu_btn);
		activityTheme=(TextView)findViewById(R.id.act_main_actvity);
	//	spinner=(Spinner)findViewById(R.id.type_option);
		userCount=(TextView)findViewById(R.id.user_count);
		act_location=(TextView)findViewById(R.id.act_location);
		act_content=(TextView)findViewById(R.id.act_content);
/*¼àÌý	*/	
		button.setOnClickListener(this);
		fanhui.setOnClickListener(this);
		activityTheme.setOnClickListener(this);
	//	spinner.setOnClickListener(this);
		act_location.setOnClickListener(this);
		act_content.setOnClickListener(this);
		
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
				break;
			}
			
		}
		
	}
}
