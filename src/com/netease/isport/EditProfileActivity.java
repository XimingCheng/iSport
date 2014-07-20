package com.netease.isport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.dina.ui.activity.UITableViewActivity;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.netease.util.RoundImageUtil;

public class EditProfileActivity extends UITableViewActivity {
	String a[];
	EditText et;
	String gender="男";
	String label="一枝红杏出墙来 不如自挂东南枝";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomClickListener listener = new CustomClickListener();
        getUITableView().setClickListener(listener);
    }
    
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(int index) {
			switch(index)
			{
			case 0:
				break;
			case 1:	a=new String[] {"男","女"};
				new AlertDialog.Builder(EditProfileActivity.this)  
				.setTitle("请选择")  
				.setIcon(android.R.drawable.ic_dialog_info)                  
				.setSingleChoiceItems(a, 0,   
				  new DialogInterface.OnClickListener() {  
				                              
				     public void onClick(DialogInterface dialog, int which) {  
				          
				    	 gender=a[which];
				 		
				    	 LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
				 		ViewItem viewItem = new ViewItem(view);
				 		ImageView mUserImage = (ImageView) view.findViewById(R.id.user_image);
				 		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
				 		mUserImage.setImageBitmap(RoundImageUtil.toRoundCorner(bitmap));
				 		getUITableView().addViewItem(viewItem);
				 		getUITableView().addBasicItem("修改性别", gender);
				 		
				    	 
				    	// Log.d(b, b);
				    	 dialog.dismiss();
				     }  
				  }  
				).setNegativeButton("取消", null).show();
				break;
			case 2: et=new EditText(EditProfileActivity.this);
			new AlertDialog.Builder(EditProfileActivity.this).setTitle("修改签名档").setView(
		              et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
		         	     public void onClick(DialogInterface dialog, int which) {
		         	     // 点击确定按钮后得到输入的值，保存
		         	      label=et.getText().toString();
		         	     LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		         		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
		         		ViewItem viewItem = new ViewItem(view);
		         		ImageView mUserImage = (ImageView) view.findViewById(R.id.user_image);
		         		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		         		mUserImage.setImageBitmap(RoundImageUtil.toRoundCorner(bitmap));
		         		getUITableView().addViewItem(viewItem);
		         	
		         		getUITableView().addBasicItem("修改签名档", label);
		         	      //Log.d(str, str);
		         	     }
		         	     })

		         	              .setNegativeButton("取消", null).show();
		         		

				break;
		
			}
			Toast.makeText(EditProfileActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
    	
    }

	@Override
	protected void populateList() {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout view = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
		ViewItem viewItem = new ViewItem(view);
		ImageView mUserImage = (ImageView) view.findViewById(R.id.user_image);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		mUserImage.setImageBitmap(RoundImageUtil.toRoundCorner(bitmap));
		getUITableView().addViewItem(viewItem);
		getUITableView().addBasicItem("修改性别", gender);
		getUITableView().addBasicItem("修改签名档", label);
	}
}