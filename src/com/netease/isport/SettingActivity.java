package com.netease.isport;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

public class SettingActivity extends Activity {

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
    
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			Toast.makeText(SettingActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
    	
    }
}
