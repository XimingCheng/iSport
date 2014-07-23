package com.netease.isport;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.netease.util.NetWorkUtil;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.RoundImageUtil;
import com.netease.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ResultListActivity extends Activity{
	private Bitmap mDefaultBit;
	private ListView mListview=null;
	private ImageView fanhui=null;
	private ListItemArrayAdapter mListItemArrayAdapter;
	ArrayList<ListItem> mItemArray = new ArrayList<ListItem>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_result_list);
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		Intent intent = getIntent();
		int queryCode =intent.getIntExtra("flag", 2);
		mDefaultBit = BitmapFactory.decodeResource(getResources(), R.drawable.user_photo);
		switch (queryCode){
		   case 1 :{
			   String date_act=intent.getStringExtra("date_act");
			   String time_act=intent.getStringExtra("time_act");
			   String class_act=intent.getStringExtra("class_act");
			   list.add(new BasicNameValuePair("date_act",date_act));
			   list.add(new BasicNameValuePair("time_act",time_act));
			   list.add(new BasicNameValuePair("class_act",class_act));
			   search(list);
		   }   break;
		   case 0:{
			   String class_act=intent.getStringExtra("class_act");
			   list.add(new BasicNameValuePair("class_act",class_act));
			   search(list);
			   break;
		   }
		   case 2 :{
			   Toast.makeText(ResultListActivity.this, 
						 "没有类别", Toast.LENGTH_LONG).show();
		   }
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gaoyuanyuan);
		
		mListview= (ListView) findViewById(R.id.pushed_list);
		fanhui=(ImageView)findViewById(R.id.title_bar_menu_btn);
		fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ResultListActivity.this.finish();
				
			}
		});
		mListview.setItemsCanFocus(false);
		mListItemArrayAdapter = new ListItemArrayAdapter(ResultListActivity.this, R.layout.list_item, mItemArray);
		mListview.setAdapter(mListItemArrayAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			 @Override
			 public void onItemClick(AdapterView<?> parent, View v,
			     final int position, long id) {
//				 Toast.makeText(ResultListActivity.this, 
//						 "List Item Clicked:" + position + " id " + id, Toast.LENGTH_LONG).show();
				 Intent intent = new Intent();
				 intent.putExtra("id", mItemArray.get((int) id).getmAcTId());
				 intent.putExtra("name", mItemArray.get((int) id).getmUserName());
				 intent.setClass(ResultListActivity.this, InfoActivity.class);
				 startActivity(intent);
			 }
		});
}
	
	private void search(List<NameValuePair> list){
		HttpResponse httpResponse=null;
		try {
			httpResponse = PostandGetConnectionUtil.getConnect(PostandGetConnectionUtil.searchUrl,list);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (PostandGetConnectionUtil.responseCode(httpResponse) != 200)
			return;
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            //String json_str = PostandGetConnectionUtil.GetResponseMessage(httpResponse); //post方式
            if(message.length() != 0) {
    			JsonPushRet o = new DecodeJson().jsonPush(message);
    			mItemArray.clear();
            if(o.getRet().equals("ok")) {
				int count = o.getCount();
				for(int i = 0; i < count; i++) {
					String theme = "主题：" + o.getList().get(i).getTheme();
					String details = "正文：" + o.getList().get(i).getDetails();
					String time = "时间：" + o.getList().get(i).getTime();
					String cnt = "人数："+ o.getList().get(i).getCount();
					String name = o.getList().get(i).getName();
					String img = o.getList().get(i).getImg();
					String id  = o.getList().get(i).getId();
					Bitmap bitmap = mDefaultBit;
					String image_location = PostandGetConnectionUtil.mediaUrlBase + img;
					// get the image from the url
					try{
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						URL url_image = new URL(image_location);  
						InputStream is = url_image.openStream();
						bitmap = RoundImageUtil.toRoundCorner(BitmapFactory.decodeStream(is));
						is.close();
					} catch(Exception e) {
			            e.printStackTrace();  
			        }
					mItemArray.add(new ListItem(name, theme, time, cnt, details, id, bitmap));
				}
			
            } 
           }
		
	}
	
}
