package com.netease.isport;

import br.com.dina.ui.activity.UITableViewActivity;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.dina.ui.widget.UITableView;
import br.com.dina.ui.widget.UITableView.ClickListener;

public class EditProlfileActivity extends UITableViewActivity {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomClickListener listener = new CustomClickListener();
        getUITableView().setClickListener(listener);
    }
    
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			Toast.makeText(EditProlfileActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
    	
    }

	@Override
	protected void populateList() {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
		ViewItem viewItem = new ViewItem(view);
		getUITableView().addViewItem(viewItem);
		getUITableView().addBasicItem("修改性别", "男");
		getUITableView().addBasicItem("修改签名档", " 一枝红杏出墙来 不如自挂东南枝 竹外桃花三两枝 春江水暖鸭先知");
	}
}
