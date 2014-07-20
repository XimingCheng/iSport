package com.netease.isport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.dina.ui.activity.UITableViewActivity;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.netease.util.RoundImageUtil;

public class EditProfileActivity extends UITableViewActivity {
	
	private String mGenderMsg[] = new String[] {"男", "女"};
	private int which_gender = 0;
	RelativeLayout mView;
	private EditText mEditLabel;
	String gender;
	String label  = "一枝红杏出墙来 不如自挂东南枝";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomClickListener listener = new CustomClickListener();
        getUITableView().setClickListener(listener);
    }
    
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(final int index) {
			switch(index)
			{
			case 0:
				break;
			case 1:
				new AlertDialog.Builder(EditProfileActivity.this)  
				.setTitle("请选择")  
				.setIcon(android.R.drawable.ic_dialog_info)                  
				.setSingleChoiceItems(mGenderMsg, which_gender, new DialogInterface.OnClickListener() {  
				                              
				     public void onClick(DialogInterface dialog, int which) { 
				    	gender = mGenderMsg[which];
				    	which_gender = which;
				 		//getUITableView().addBasicItem("修改性别", gender);
				    	getUITableView().setSubTitle(index, gender);
				    	
				    	dialog.dismiss();
				     }  
				  }  
				).setNegativeButton("取消", null).show();
				break;
			case 2:
				mEditLabel = new EditText(EditProfileActivity.this);
				mEditLabel.setText(label);
				new AlertDialog.Builder(EditProfileActivity.this).setTitle("修改签名档").setView(
					mEditLabel).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
		         	    	// 点击确定按钮后得到输入的值，保存
							label = mEditLabel.getText().toString();
							getUITableView().setSubTitle(index, label);
		         	     }})
		            .setNegativeButton("取消", null).show();
				break;
		
			}
			//Toast.makeText(EditProfileActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
    	
    }

	@Override
	protected void populateList() {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
		ViewItem viewItem = new ViewItem(mView);
		ImageView mUserImage = (ImageView) mView.findViewById(R.id.user_image);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		mUserImage.setImageBitmap(RoundImageUtil.toRoundCorner(bitmap));
		getUITableView().addViewItem(viewItem);
		gender = mGenderMsg[which_gender];
		getUITableView().addBasicItem("修改性别", gender);
		getUITableView().addBasicItem("修改签名档", label);
	}
}