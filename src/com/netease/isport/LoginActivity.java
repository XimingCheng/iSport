package com.netease.isport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.GetIntentInstance;
import com.netease.util.MD5util;
import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;

public class LoginActivity extends Activity implements OnClickListener{
	TextView textView,register=null;
	ImageView preStep=null;
	private ProgressDialog pd;
	static final private int regOk = 5;
	
	Intent intent = GetIntentInstance.getIntent();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		textView=(TextView)findViewById(R.id.login);
		preStep=(ImageView)findViewById(R.id.title_bar_menu_btn);
		register=(TextView)findViewById(R.id.register);
		
		preStep.setOnClickListener(this);
		textView.setOnClickListener(this);
		register.setOnClickListener(this);
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
			  //startActivity(intent);
			  startActivityForResult(intent, regOk);
			  break;
		  }
		}
	}
	
	Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法  
            pd.dismiss();// 关闭ProgressDialog
            String out = (String)msg.obj;
            if(out.equals("ok")) {
            	ToastUtil.show(getApplicationContext(), "登录成功！");
            	setResult(RESULT_OK, intent);
            	LoginActivity.this.finish();
            } else if (out.equals("not_exist")) {
            	ToastUtil.show(getApplicationContext(), "帐号不存在，请重新登录");	
            } else if (out.equals("password_error")) {
            	ToastUtil.show(getApplicationContext(), "密码不正确，请重新输入");
            } else {
            	ToastUtil.show(getApplicationContext(), "登录失败了啊！");
            }
        }  
    }; 
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(regOk == requestCode && RESULT_OK == resultCode) {
    		setResult(RESULT_OK, intent);
        	LoginActivity.this.finish();
    	}
    }
    
	public void login(){
		if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			return;
		}
		
		pd = ProgressDialog.show(LoginActivity.this, "登录中", "加载中，请稍后……");
		new Thread(new Runnable() {  
            @Override  
            public void run() {
            	AutoCompleteTextView user_name=(AutoCompleteTextView)findViewById(R.id.username);
        		AutoCompleteTextView user_password=(AutoCompleteTextView)findViewById(R.id.password);
        		String username=user_name.getText().toString();
        		String password=user_password.getText().toString();
            	HttpResponse httpResponse=null;
            	List<NameValuePair> list=new ArrayList<NameValuePair>();
        	    String passwordMd5 = MD5util.encryptToMD5(password);
        		list.add(new BasicNameValuePair("username",username));
        		list.add(new BasicNameValuePair("password",passwordMd5));
        		if(username.length() == 0 || password.length() == 0) {
        			ToastUtil.show(getApplicationContext(), "请输入用户名和密码");
        			return;
        		}
        		PostandGetConnectionUtil.setParm(list);
        		try {
        			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.loginUrl);
        		} catch (URISyntaxException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse) == 200){
        			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    JsonRet o = new DecodeJson().jsonRet(message);
                    if(o.getRet().equals("ok")) {
                    	SharedPreferenceUtil.setLogin(true);
                    	String o1 = "ok";
                    	Message msg = new Message();
                    	msg.obj = o1;
                    	handler.sendMessage(msg);
                    }
                    else if(o.getRet().equals("not_exist")){
                    	String o1 = "not_exist";
                    	Message msg = new Message();
                    	msg.obj = o1;
                    	handler.sendMessage(msg);
                    	//ToastUtil.show(getApplicationContext(), "帐号不存在，请重新登录");	
                    }
                    else if(o.getRet().equals("password_error")){
                    	String o1 = "password_error";
                    	Message msg = new Message();
                    	msg.obj = o1;
                    	handler.sendMessage(msg);
                    	//ToastUtil.show(getApplicationContext(), "密码不正确，请重新输入");
                    }
                    else {
                    	String o1 = "error";
                    	Message msg = new Message();
                    	msg.obj = o1;
                    	handler.sendMessage(msg);
                    	//ToastUtil.show(getApplicationContext(), "登录失败了啊！");
                    }
        		} else {
        			String o1 = "error";
                	Message msg = new Message();
                	msg.obj = o1;
                	handler.sendMessage(msg);
        			//ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
        		}
                //handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler  
            }  

        }).start();
		
	}
}
