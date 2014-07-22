package com.netease.isport;

import java.net.URISyntaxException;
import org.apache.http.HttpResponse;

import com.netease.util.GetIntentInstance;
import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

public class SettingActivity extends Activity {

	private ProgressDialog progDialog = null;
	private UITableView tableView;
	private ImageView title_bar_menu_btn=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        
        tableView = (UITableView) findViewById(R.id.tableView);
        title_bar_menu_btn=(ImageView) findViewById(R.id.title_bar_menu_btn);
        title_bar_menu_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(-2, GetIntentInstance.getIntent());
				SettingActivity.this.finish();
			}
		});
        createList();
        
        Log.d("MainActivity", "total items: " + tableView.getCount());
        
        tableView.commit();
    }
    
    private void createList() {
    	CustomClickListener listener = new CustomClickListener();
    	tableView.setClickListener(listener);
    	//tableView.addBasicItem(R.drawable.clock, "活动提前提醒", "提前30分钟");
    	tableView.addBasicItem(R.drawable.version, "程序版本", "版本太大，超出手机存储范围1.0");
    	tableView.addBasicItem(R.drawable.info, "关于iSport", "iSport的介绍说明");
    	tableView.addBasicItem(R.drawable.logout, "退出登录", "退出当前的账户");
    }
    
    private void showProgressDialog() {
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在退出您的账户");
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
	public void live_or_die()
	{
		new AlertDialog.Builder(this).setTitle("不要退出啊，亚美蝶！").setIcon(android.R.drawable.ic_dialog_info).
		setItems(new String[] { "换个号登陆", "退出程序" }, new DialogInterface.OnClickListener() {//添加button并响应点击事件
		      @Override
		      public void onClick(DialogInterface dialog, int which) {
		      if(0 == which)
		      {
		    	  logout();
		    	  SettingActivity.this.finish();
		      }
		      else if(1 == which)
		      {
		    	  logout();
		    	  setResult(RESULT_CANCELED, GetIntentInstance.getIntent());
	              SettingActivity.this.finish();
		      }
		     }
		      }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加button并响应点击事件
			      @Override
			      public void onClick(DialogInterface dialog, int which) {
			    	  return;
			     }
			    }).show();
	}
	public void logout()
	{
		if( !NetWorkUtil.isNetworkConnected(SettingActivity.this.getApplicationContext()) ) {
			ToastUtil.show(getApplicationContext(), "网络服务不可用，请检查网络状态！");
			return;
		}
		HttpResponse httpResponse = null;
		try {
			showProgressDialog();
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.logoutUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(PostandGetConnectionUtil.responseCode(httpResponse) == 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            if(o.getRet().equals("ok")) {
            	SharedPreferenceUtil.setLogin(false);
            	ToastUtil.show(getApplicationContext(), "退出登录成功！");
            	setResult(RESULT_OK, GetIntentInstance.getIntent());
            } else {
            	SharedPreferenceUtil.setLogin(false);
            	setResult(RESULT_OK, GetIntentInstance.getIntent());
            	//SettingActivity.this.finish();
            	//ToastUtil.show(getApplicationContext(), "退出登录失败了啊啊啊啊啊！");
            }
		} else {
			SharedPreferenceUtil.setLogin(false);
        	setResult(RESULT_OK, GetIntentInstance.getIntent());
        	//SettingActivity.this.finish();
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
		dissmissProgressDialog();
	}
	
    private class CustomClickListener implements ClickListener {
    	
		@Override
		public void onClick(int index) {
			//Toast.makeText(SettingActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
			switch(index) {
			case 2: //logout
				live_or_die();
				break;
			}
		}
    	
    }
}
