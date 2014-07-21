package com.netease.isport;

import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import br.com.dina.ui.activity.UITableViewActivity;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.netease.util.RoundImageUtil;

public class EditProfileActivity extends UITableViewActivity {
	
	private String mGenderMsg[] = new String[] {"鐢�", "濂�"};
	private int which_gender = 0;
	RelativeLayout mView;
	private EditText mEditLabel;
	String gender;
	String label  = "涓�鏋濈孩鏉忓嚭澧欐潵 涓嶅鑷寕涓滃崡鏋�";
	private ImageView title_bar_menu_btn;
	private static final int REQUEST_CODE = 1;//选择文件的返回码
	ImageView mUserImage = (ImageView) mView.findViewById(R.id.user_image);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_bar_menu_btn=(ImageView)findViewById(R.id.title_bar_menu_btn);
        title_bar_menu_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditProfileActivity.this.finish();
			}
		});
        CustomClickListener listener = new CustomClickListener();
        getUITableView().setClickListener(listener);
    }
    
    private class CustomClickListener implements ClickListener {

		@Override
		public void onClick(final int index) {
			switch(index)
			{
			case 0:
				Intent intent = new Intent();
			    intent.setType("image/*");
			    intent.setAction(Intent.ACTION_GET_CONTENT);
			    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
			    startActivityForResult(Intent.createChooser(intent,
			            "选择一张图片作为头像"), REQUEST_CODE);
				break;
			case 1:
				new AlertDialog.Builder(EditProfileActivity.this)  
				.setTitle("璇烽�夋嫨")  
				.setIcon(android.R.drawable.ic_dialog_info)                  
				.setSingleChoiceItems(mGenderMsg, which_gender, new DialogInterface.OnClickListener() {  
				                              
				     public void onClick(DialogInterface dialog, int which) { 
				    	gender = mGenderMsg[which];
				    	which_gender = which;
				 		//getUITableView().addBasicItem("淇敼鎬у埆", gender);
				    	getUITableView().setSubTitle(index, gender);
				    	
				    	dialog.dismiss();
				     }  
				  }  
				).setNegativeButton("鍙栨秷", null).show();
				break;
			case 2:
				mEditLabel = new EditText(EditProfileActivity.this);
				mEditLabel.setText(label);
				new AlertDialog.Builder(EditProfileActivity.this).setTitle("淇敼绛惧悕妗�").setView(
					mEditLabel).setPositiveButton("纭畾", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
		         	    	// 鐐瑰嚮纭畾鎸夐挳鍚庡緱鍒拌緭鍏ョ殑鍊硷紝淇濆瓨
							label = mEditLabel.getText().toString();
							getUITableView().setSubTitle(index, label);
		         	     }})
		            .setNegativeButton("鍙栨秷", null).show();
				break;
		
			}
			//Toast.makeText(EditProfileActivity.this, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
    	
    }
    @Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 switch(requestCode) {
		 case REQUEST_CODE:
			 if (resultCode == 0)  
				 return; 
			 Uri uri = data.getData();
			 Bitmap bmp = null;
			 ContentResolver cr = this.getContentResolver();   
		     try {
	            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
		     } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
		     }
		     if(bmp != null) {
			     Bitmap output = RoundImageUtil.toRoundCorner(RoundImageUtil.
			    		 resizeImage(bmp, 100, 100));
			     mUserImage.setImageBitmap(output);
		     }
	         break;
		 }
	}

	@Override
	protected void populateList() {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = (RelativeLayout) mInflater.inflate(R.layout.cust_edit_photo, null);
		ViewItem viewItem = new ViewItem(mView);
		mUserImage = (ImageView) mView.findViewById(R.id.user_image);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		mUserImage.setImageBitmap(RoundImageUtil.toRoundCorner(bitmap));
		getUITableView().addViewItem(viewItem);
		gender = mGenderMsg[which_gender];
		getUITableView().addBasicItem("淇敼鎬у埆", gender);
		getUITableView().addBasicItem("淇敼绛惧悕妗�", label);
	}
}