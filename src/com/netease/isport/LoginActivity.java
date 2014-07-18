package com.netease.isport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.GetIntentInstance;
import com.netease.util.MD5util;
import com.netease.util.PostConnectionUtil;

public class LoginActivity extends Activity implements OnClickListener{
	TextView textView,register=null;
	ImageView preStep=null;
	
	Intent intent=GetIntentInstance.getIntent();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		textView=(TextView)findViewById(R.id.login);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		register=(TextView)findViewById(R.id.register);
		
		preStep.setOnClickListener(this);
		textView.setOnClickListener(this);
		register.setOnClickListener(this);
		SharedPreferences sp=getSharedPreferences("test", 0);
		
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		  case R.id.login :{ 
				login();  break;
		  }
		  case R.id.title_bar_menu_btn :{  //return
			  LoginActivity.this.finish();
			  break;
		  }
		  
		  case R.id.register :{
			  intent.setClass(LoginActivity.this, RegisterActivity.class);
			  startActivity(intent);  break;
		  }
		}
	}
	
	public void login(){
		HttpResponse httpResponse=null;
		AutoCompleteTextView user_name=(AutoCompleteTextView)findViewById(R.id.username);
		AutoCompleteTextView user_password=(AutoCompleteTextView)findViewById(R.id.password);
		String username=user_name.getText().toString();
		String password=user_password.getText().toString();
		List<NameValuePair> list=new ArrayList<NameValuePair>();
	    password=MD5util.encryptToMD5(password);
		list.add(new BasicNameValuePair("username",username));
		list.add(new BasicNameValuePair("password",password));
		PostConnectionUtil.setParm(list);
		try {
			httpResponse=PostConnectionUtil.postConnect(PostConnectionUtil.loginUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(PostConnectionUtil.responseCode(httpResponse)==200){
			String message=null;
        	message=PostConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), "µÇÂ¼³É¹¦", Toast.LENGTH_SHORT).show();
            intent.setClass(LoginActivity.this, MainActivity.class);
		    startActivity(intent);
		}
		
		
	}
}
