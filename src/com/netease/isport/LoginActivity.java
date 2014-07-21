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
	private ProgressDialog progDialog = null;
	
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
			  startActivity(intent);  break;
		  }
		}
	}
	
	private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在登录您的账户");
		progDialog.show();
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}
	
	public void login(){
		if( !NetWorkUtil.isNetworkConnected(this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			return;
		}
		HttpResponse httpResponse=null;
		AutoCompleteTextView user_name=(AutoCompleteTextView)findViewById(R.id.username);
		AutoCompleteTextView user_password=(AutoCompleteTextView)findViewById(R.id.password);
		String username=user_name.getText().toString();
		String password=user_password.getText().toString();
		if(username.length() == 0 || password.length() == 0) {
			ToastUtil.show(getApplicationContext(), "请输入用户名和密码");
			return;
		}
		showProgressDialog();
		List<NameValuePair> list=new ArrayList<NameValuePair>();
	    password=MD5util.encryptToMD5(password);
		list.add(new BasicNameValuePair("username",username));
		list.add(new BasicNameValuePair("password",password));
		PostandGetConnectionUtil.setParm(list);
		try {
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.loginUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse) == 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            if(o.getRet().equals("ok")) {
            	SharedPreferenceUtil.setLogin(true);
            	ToastUtil.show(getApplicationContext(), "登录成功！");
            	setResult(RESULT_OK, intent);
            	LoginActivity.this.finish();
            } else {
            	ToastUtil.show(getApplicationContext(), "登录失败了啊啊啊啊啊！");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
		dissmissProgressDialog();
	}
}
