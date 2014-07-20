package com.netease.isport;

import java.util.Calendar;
import java.util.Locale;

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

public class PublicActivity extends Activity implements OnClickListener{
    private Button button=null;
    private ImageView fanhui=null;
    private TextView activityTheme=null;
    private Spinner spinner=null;
    private TimePickerDialog.OnTimeSetListener t=null;
    private TextView userCount,act_location,act_content=null;
    private TextView act_time,act_date=null;
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
	//	spinner=(Spinner)findViewById(R.id.type_option);
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
	    			   String str=parent.getItemAtPosition(position).toString();
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
}
