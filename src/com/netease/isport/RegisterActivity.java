package com.netease.isport;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.netease.util.GetIntentInstance;
import com.netease.util.MD5util;
import com.netease.util.PostConnectionUtil;

public class RegisterActivity extends Activity implements OnClickListener{
	private Button button=null;
	private ImageView preStep=null;
	private AutoCompleteTextView autoTextAccount;
	private AutoCompleteTextView autoTextpassword;
	private AutoCompleteTextView autoTextcomfirm;
	private String password=null;
	private String confirm=null;
	private String username=null;
	private Intent intent=null;
	
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
                    username=autoTextAccount.getText().toString().trim();
                    if(username.length()<3){
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
                    if(password.length()<3){
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
	    intent=GetIntentInstance.getIntent();
		if(v.getId()==R.id.complete){
			if(confirm==null){
				confirm=autoTextcomfirm.getText().toString().trim();
			}
			else if(!confirm.equals(password)){
				//Toast.makeText(getApplicationContext(), confirm, Toast.LENGTH_SHORT).show();
				autoTextpassword.setText("");
				autoTextcomfirm.setText(""); 
				//Toast.makeText(getApplicationContext(), "Tst", Toast.LENGTH_SHORT).show();
				//intent.setClass(RegisterActivity.this, LoginActivity.class);
				//startActivity(intent);
			  }
			else{
				//Toast.makeText(getApplicationContext(), "Tst", Toast.LENGTH_SHORT).show();
			    try {
					register();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			}
		else if(v.getId()==R.id.title_bar_menu_btn){
			//注册页面
		    intent.setClass(RegisterActivity.this, LoginActivity.class);
		    startActivity(intent);
		}
	}
	
	public  void register() throws URISyntaxException {
		HttpResponse httpResponse=null;		
	//	String url="http://efly.freeshell.ustc.edu.cn:54322/reg/";
		//HttpPost httpRequest=new HttpPost(new URI(url));
		List<NameValuePair> list=new ArrayList<NameValuePair>();
	    password=MD5util.encryptToMD5(password);
		
		list.add(new BasicNameValuePair("username",username));
		list.add(new BasicNameValuePair("password",password));
		PostConnectionUtil.setParm(list);
		httpResponse=PostConnectionUtil.postConnect(PostConnectionUtil.regUrl);
		
        if(PostConnectionUtil.responseCode(httpResponse)==200){
        	String message=null;
        	message=PostConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
            intent.setClass(RegisterActivity.this, MainActivity.class);
    	    startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "请求错误", Toast.LENGTH_SHORT).show();
        }
         
    }
	
}