package com.netease.isport;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.netease.util.DateAndTimeUtil;
import com.netease.util.GetIntentInstance;
import com.netease.util.MyAdapter;
import com.netease.util.PostandGetConnectionUtil;
import com.netease.util.SharedPreferenceUtil;
import com.netease.util.ToastUtil;

public class PublicActivity extends Activity implements OnClickListener{
    private Button button=null;
    private ImageView fanhui=null;
    private TextView activityTheme=null;
    private Spinner spinner=null;
    private TimePickerDialog.OnTimeSetListener t=null;
    private TextView userCount,act_location,act_content=null;
    private TextView act_time,act_date=null;
    private String act_category;
    private static DatePickerDialog.OnDateSetListener d;
    static Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    private Intent intent=GetIntentInstance.getIntent();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_submit_act);
		
		button=(Button)findViewById(R.id.submit_act);    //发布按钮
		fanhui=(ImageView)findViewById(R.id.title_bar_menu_btn);  //返回按钮
		activityTheme=(TextView)findViewById(R.id.act_main_actvity);    
		//spinner=(Spinner)findViewById(R.id.type_option);
		act_time=(TextView)findViewById(R.id.act_startTime_actvity);
		act_date=(TextView)findViewById(R.id.act_startDate_actvity);
		userCount=(TextView)findViewById(R.id.user_count);
		act_location=(TextView)findViewById(R.id.act_location);
		act_content=(TextView)findViewById(R.id.act_content);
		spinner=(Spinner)findViewById(R.id.type_option);
/*监听	*/	
		button.setOnClickListener(this);
		fanhui.setOnClickListener(this);
		activityTheme.setOnClickListener(this);
	//	spinner.setOnClickListener(this);
		act_location.setOnClickListener(this);
		act_content.setOnClickListener(this);
		act_time.setOnClickListener(this);
		act_date.setOnClickListener(this);
		
		DateAndTimeUtil.setTextView(act_date);
		DateAndTimeUtil.setTimeTextView(act_time);
		DateAndTimeUtil.updateDateLabel();
		DateAndTimeUtil.updateTimeLabel();
		
		String[] mItems = getResources().getStringArray(R.array.planets_arry);
		MyAdapter _Adapter=new MyAdapter(this,mItems);
		spinner.setAdapter(_Adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
	    		@Override
	    		public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
	    			 act_category=parent.getItemAtPosition(position).toString();
	    	    	}
	    		@Override
	    		public void onNothingSelected(AdapterView<?> parent) {
	    			// TODO Auto-generated method stub
	    	    	}
	  });
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubb
		switch(v.getId()) {
			case R.id.submit_act :{
				//业务逻辑
				public_act();
				break;
			}
			case R.id.title_bar_menu_btn : {
				PublicActivity.this.finish();
				break;
			}
			case R.id.act_startDate_actvity :{
				//DateAndTimeUtil.setTextView(act_time);
				d=DateAndTimeUtil.lister();
				new DatePickerDialog(PublicActivity.this,
                        d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)).show();  
				break;
			}
			case R.id.act_startTime_actvity :{
				//DateAndTimeUtil.setTextView(act_time);
				t=DateAndTimeUtil.listerTime();
		                new TimePickerDialog(PublicActivity.this,
		                        t,
		                        dateAndTime.get(Calendar.HOUR_OF_DAY),
		                        dateAndTime.get(Calendar.MINUTE),
		                        true).show();
		            }
			break;
			
		}
		
	}
	
	private void public_act(){
		HttpResponse httpResponse=null;
		String theme=activityTheme.getText().toString();
		String date=act_date.getText().toString();
		String category=act_category;
		String detail=act_content.getText().toString();
		String num=userCount.getText().toString();
		String address=act_location.getText().toString();
		String time=act_time.getText().toString()+":00";
		/*Toast.makeText(PublicActivity.this, 
				 "theme:" + theme + " date " + date+ "category"+category+"  detail:"+detail+"  usercount:"+num+"  adress:"+adress, Toast.LENGTH_LONG).show();*/
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("theme_act",theme));
		list.add(new BasicNameValuePair("date_act",date));
		list.add(new BasicNameValuePair("time_act",time));
		list.add(new BasicNameValuePair("class_act",category));
		list.add(new BasicNameValuePair("detail_act",detail));
		list.add(new BasicNameValuePair("address_act", address));
		list.add(new BasicNameValuePair("num_act", num));
		PostandGetConnectionUtil.setParm(list);
		Toast.makeText(PublicActivity.this, 
				 "theme:" + theme + " date " + date+ "category"+category+"  detail:"+detail+"  usercount:"+num+"  adress:"+address, Toast.LENGTH_LONG).show();
		try {
			httpResponse = PostandGetConnectionUtil.postConnect(PostandGetConnectionUtil.publicUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(httpResponse!=null&&PostandGetConnectionUtil.responseCode(httpResponse)== 200){
			String message = PostandGetConnectionUtil.GetResponseMessage(httpResponse);            
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            JsonRet o = new DecodeJson().jsonRet(message);
            if(o.getRet().equals("ok")) {
            	ToastUtil.show(getApplicationContext(), "发布成功！");
            	PublicActivity.this.finish();
            } else {
            	ToastUtil.show(getApplicationContext(), "发布失败");
            }
		} else {
			ToastUtil.show(getApplicationContext(), "网络服务有问题，我也不知道怎么搞哦！");
		}
	}
	
}
