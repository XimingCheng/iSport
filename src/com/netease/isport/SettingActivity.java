package com.netease.isport;

import java.net.URISyntaxException;

import org.apache.http.HttpResponse;

import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

public class SettingActivity extends Activity {

<<<<<<< HEAD
	private ProgressDialog progDialog = null;
	UITableView tableView;
=======
	private UITableView tableView;
	private ImageView title_bar_menu_btn=null;
>>>>>>> 7876dc7b2cff82b7b9c708e7443ad9963b150e51
	
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
    	tableView.addBasicItem(R.drawable.clock, "活动提前提醒", "提前30分钟");
    	tableView.addBasicItem(R.drawable.arm, "提醒方式", "设置活动提醒方式");
    	tableView.addBasicItem(R.drawable.account_set, "修改个人信息", "设置头像性别和签名档");
    	tableView.addBasicItem(R.drawable.prefer, "个人运动偏好", "选择个人运动爱好类别和推送的距离和时间范围");
    	tableView.addBasicItem(R.drawable.version, "程序版本", "版本太大，超出手机存储范围1.0");
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
	
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			//Toast.makeText(SettingActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
			switch(index) {
			case 5: //logout
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
		            	SettingActivity.this.finish();
		            } else {
		            	ToastUtil.show(getApplicationContext(), "退出登录失败了啊啊啊啊啊！");
		            }
				} else {
					ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
				}
				dissmissProgressDialog();
				break;
			}
		}
    	
    }
}
