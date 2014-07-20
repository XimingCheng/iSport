package com.netease.isport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import com.netease.util.GetIntentInstance;
import com.netease.util.MyAdapter;

public class SearchActivity extends Activity implements OnClickListener{
   // Button button=null;
    private TextView date,time;
    private ImageView imageView;
    private Button button;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    
    /*   处理日历       */
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh时mm分");
    //定义一个TextView控件对象
    TextView dateAndTimeLabel,timeLabel = null;
    //获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);   
            //将页面TextView的显示更新为最新时间
            updateLabel();           
        }       
    };
    
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        
        //同DatePickerDialog控件
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            updateLabelTime();
            
        }
    };
    
    
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		spinner = (Spinner) findViewById(R.id.spinner1);   //分类框
		button=(Button) findViewById(R.id.search);         //搜索框
		date=(TextView)findViewById(R.id.date);
		time=(TextView)findViewById(R.id.time);
		imageView=(ImageView) findViewById(R.id.title_bar_menu_btn);
		
		date.setOnClickListener(this);
		time.setOnClickListener(this);
		
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
		button.setOnClickListener(this);
		imageView.setOnClickListener(this);
		
        dateAndTimeLabel=(TextView)findViewById(R.id.date);
      //  timeLabel=(TextView)findViewById(R.id.time);
        updateLabel();
        updateLabelTime();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubb
		Intent intent=GetIntentInstance.getIntent();
		switch(v.getId()) {
			case R.id.search :{
				//搜索业务逻辑
				break;
			}
			case R.id.title_bar_menu_btn : {
				SearchActivity.this.finish();
				break;
			}
			case R.id.date:{
                new DatePickerDialog(SearchActivity.this,
                        d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH)).show();               
			}
			case R.id.time:{time.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                new TimePickerDialog(SearchActivity.this,
	                        t,
	                        dateAndTime.get(Calendar.HOUR_OF_DAY),
	                        dateAndTime.get(Calendar.MINUTE),
	                        true).show();
	            }
	        });}
		}
	}
	
	private void updateLabel() {
		//format.format(dateAndTime.getTime());
        dateAndTimeLabel.setText(format.format(dateAndTime.getTime()));
        }
	private void updateLabelTime(){
		//timeFormat.format(dateAndTime.getTime());
		time.setText(timeFormat.format(dateAndTime.getTime()));
	}
}
