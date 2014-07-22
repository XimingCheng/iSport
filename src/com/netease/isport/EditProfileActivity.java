package com.netease.isport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.dina.ui.activity.UITableViewActivity;
import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView.ClickListener;

import com.netease.util.GetIntentInstance;
import com.netease.util.MD5util;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.RoundImageUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;

public class EditProfileActivity extends UITableViewActivity {
	
	private String mGenderMsg[] = new String[] {"男", "女"};
	private int which_gender = 0;
	RelativeLayout mView;
	private EditText mEditLabel;
	String gender;
	String mLable  = "你妹啊啊啊啊啊啊啊啊啊啊！";
	private ImageView title_bar_menu_btn;
	private static final int REQUEST_CODE = 1;//选择文件的返回码
	private ImageView mUserImage;
	
	void syn_ctrl_sex() {
		if( SharedPreferenceUtil.isLogin() ) {
			String sex = SharedPreferenceUtil.getSharedPreferences().getString("sex", "");
			which_gender = (sex.equals("F") ? 1 : 0);
			gender = mGenderMsg[which_gender];
			getUITableView().setSubTitle(1, gender);
		}
	}
	
	void syn_ctrl_label() {
		if( SharedPreferenceUtil.isLogin() ) {
			String label = SharedPreferenceUtil.getSharedPreferences().getString("label", "");
			getUITableView().setSubTitle(2, label);
			mLable = label;
		}
	}
	
	void syn_ctrl_image() {
		if( SharedPreferenceUtil.isLogin() ) {
			String imageBase64 = SharedPreferenceUtil.getSharedPreferences().getString("imageBase64", "");
			if(imageBase64.length() != 0) {
				byte[] base64Bytes = Base64.decode(imageBase64.getBytes(), Base64.DEFAULT);
				ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
				Bitmap bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(bais));
				mUserImage.setImageBitmap(bitmap);
			}
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserImage = (ImageView) mView.findViewById(R.id.user_image);
        title_bar_menu_btn=(ImageView)findViewById(R.id.title_bar_menu_btn);
        
        syn_ctrl_image();
        syn_ctrl_sex();
        syn_ctrl_label();
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
				.setTitle("请选择")  
				.setIcon(android.R.drawable.ic_dialog_info)                  
				.setSingleChoiceItems(mGenderMsg, which_gender, new DialogInterface.OnClickListener() {  
				                              
				     public void onClick(DialogInterface dialog, int which) { 
				    	gender = mGenderMsg[which];
				    	HttpResponse httpResponse=null;
				    	List<NameValuePair> list=new ArrayList<NameValuePair>();
				    	String sexMsg[] = new String[] {"M", "F"};
				    	String out_sex = sexMsg[which];
						list.add(new BasicNameValuePair("sex",out_sex));
						PostandGetConnectionUtil.setParm(list);
						try {
							httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.editsexUrl);
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse) == 200){
							String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
				            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
				            JsonRet o = new DecodeJson().jsonRet(message);
				            if(o.getRet().equals("ok")) {
				            	getUITableView().setSubTitle(index, gender);
				            	which_gender = which;
				            	SharedPreferences.Editor editor = SharedPreferenceUtil.getSharedPreferences().edit();
				            	editor.putString("sex", sexMsg[which_gender]);
				            	editor.commit();
				            	ToastUtil.show(getApplicationContext(), "修改性别成功！");
				            } else {
				            	ToastUtil.show(getApplicationContext(), "修改性别失败了啊啊啊啊啊！");
				            }
						} else {
							ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
						}
				    	dialog.dismiss();
				     }  
				  }  
				).setNegativeButton("取消", null).show();
				break;
			case 2:
				mEditLabel = new EditText(EditProfileActivity.this);
				mEditLabel.setText(mLable);
				new AlertDialog.Builder(EditProfileActivity.this).setTitle("修改签名档").setView(
					mEditLabel).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
		         	    	// 点击确定按钮后得到输入的值，保存
							mLable = mEditLabel.getText().toString();
							HttpResponse httpResponse=null;
					    	List<NameValuePair> list=new ArrayList<NameValuePair>();
							list.add(new BasicNameValuePair("label",mLable));
							PostandGetConnectionUtil.setParm(list);
							try {
								httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.editlabelUrl);
							} catch (URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse) == 200){
								String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
					            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
					            JsonRet o = new DecodeJson().jsonRet(message);
					            if(o.getRet().equals("ok")) {
					            	getUITableView().setSubTitle(index, mLable);
					            	SharedPreferences.Editor editor = SharedPreferenceUtil.getSharedPreferences().edit();
					            	editor.putString("label", mLable);
					            	editor.commit();
					            	ToastUtil.show(getApplicationContext(), "修改签名档成功！");
					            } else {
					            	ToastUtil.show(getApplicationContext(), "修改签名档失败了啊啊啊啊啊！");
					            }
							} else {
								ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
							}
		         	     }})
		            .setNegativeButton("取消", null).show();
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
			 String[] proj = { MediaStore.Images.Media.DATA };
			 Cursor actualimagecursor = managedQuery(uri,proj,null,null,null); 
			 int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			 actualimagecursor.moveToFirst();
			 String img_path = actualimagecursor.getString(actual_image_column_index);
			 
			 HttpResponse httpResponse=null;
			 try {
				httpResponse = PostandGetConnectionUtil.postFile(PostandGetConnectionUtil.uploadUrl, img_path);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(httpResponse != null && PostandGetConnectionUtil.responseCode(httpResponse) == 200){
					String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
		            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		            JsonRet o = new DecodeJson().jsonRet(message);
		            if(o.getRet().equals("ok")) {
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
		   			 		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   			 		output.compress(CompressFormat.PNG, 100, baos);
		   			 		String imageBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		   			 		SharedPreferences sp = SharedPreferenceUtil.getSharedPreferences();
		   			 		SharedPreferences.Editor editor = sp.edit();
		   			 		editor.putString("imageBase64", imageBase64);
		   			 		editor.commit();
		   			 	}
		   			 	setResult(RESULT_OK, GetIntentInstance.getIntent());
		            	ToastUtil.show(getApplicationContext(), "上传成功！");
		            } else {
		            	ToastUtil.show(getApplicationContext(), "上传失败了啊啊啊啊啊！");
		            }
				} else {
					ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
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
		getUITableView().addBasicItem("修改性别", gender);
		getUITableView().addBasicItem("修改签名档", mLable);
	}
}