package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	Button button=null;
	ImageView preStep=null;
	AutoCompleteTextView autoTextAccount;
	AutoCompleteTextView autoTextpassword;
	AutoCompleteTextView autoTextcomfirm;
	private String password;
	private String confirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
		setContentView(R.layout.layout_register);
		
		button=(Button)findViewById(R.id.complete);  //完成注册
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);  //上一步;
		autoTextAccount=(AutoCompleteTextView)findViewById(R.id.account); //帐号
		autoTextpassword=(AutoCompleteTextView)findViewById(R.id.password); //密码
		autoTextcomfirm=(AutoCompleteTextView)findViewById(R.id.confirm_pass);  //确认密码
		
		button.setOnClickListener(this);
		preStep.setOnClickListener(this);		
		autoTextAccount.setOnClickListener(this);
		autoTextpassword.setOnClickListener(this);
		autoTextcomfirm.setOnClickListener(this);
		 
        autoTextAccount.setOnFocusChangeListener(new OnFocusChangeListener() {   //帐号监控
			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
                    String username=autoTextAccount.getText().toString().trim();
                    if(username.length()<5){
                       // Toast.makeText(getApplicationContext(), "用户名不能小于5个字符", Toast.LENGTH_SHORT);
                        autoTextAccount.setText("");
                        
                    }
                }
			}
		});
        
        autoTextpassword.setOnFocusChangeListener(new OnFocusChangeListener() {   //密码监控
			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					password=autoTextpassword.getText().toString().trim();
                    if(password.length()<5){
                    	autoTextpassword.setText("");
                        
                    }
                }
			}
		});
        
        
        autoTextcomfirm.setOnFocusChangeListener(new OnFocusChangeListener() {  //确认密码确认
			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					confirm=autoTextcomfirm.getText().toString().trim();
				//	Toast.makeText(getApplicationContext(), confirm, Toast.LENGTH_LONG).show();
                    if(!confirm.equals(password)){
                    	//Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_LONG).show();
                    	autoTextpassword.setText("");
                    	autoTextcomfirm.setText("");                      
                    }
                }
			}
		});
	}

	@Override
	public void onClick(View v) {
	
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		
		if(v.getId()==R.id.complete){
			if(!confirm.equals(password)){
				//Toast.makeText(getApplicationContext(), confirm, Toast.LENGTH_LONG).show();
				
            	   autoTextpassword.setText("");
            	   autoTextcomfirm.setText(""); 
            	   //intent.setClass(RegisterActivity.this, LoginActivity.class);
       		       //startActivity(intent);
			    }
			else{
				//Toast.makeText(getApplicationContext(), confirm, Toast.LENGTH_LONG).show();
//				register();
     	//	    intent.setClass(RegisterActivity.this, MainActivity.class);
	    	//    startActivity(intent);
			    }
			}
		else if(v.getId()==R.id.title_bar_menu_btn){
			//注册页面
		
			
		    intent.setClass(RegisterActivity.this, LoginActivity.class);
		    startActivity(intent);
		}
	}
	
	public  void register(){
		
		
	}
}