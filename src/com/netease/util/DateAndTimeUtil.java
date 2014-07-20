package com.netease.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateAndTimeUtil {
	
	    final static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	    final static SimpleDateFormat timeFormat = new SimpleDateFormat("hh时mm分");
	    static TextView timeLabel;
	    static TextView dateAndTimeLabel;
	    static Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
	    public static void setTextView(TextView textView){
	    	dateAndTimeLabel=textView;
	    }
	    public static void setTimeTextView(TextView textView){
	    	timeLabel=textView;
	    }
	    
		public static DatePickerDialog.OnDateSetListener lister(){
		DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
		{
		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        dateAndTime.set(Calendar.YEAR, year);
		        dateAndTime.set(Calendar.MONTH, monthOfYear);
		        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateDateLabel();
		    }       
		};
		 return d;
		}
		
		public static TimePickerDialog.OnTimeSetListener listerTime(){
		  TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		    
		    //同DatePickerDialog控件
		        @Override
		        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
		            dateAndTime.set(Calendar.MINUTE, minute);
		            updateTimeLabel();
		            
		        }
		    };
		 
		  
		return t;
}
		
		    
		 public static void updateDateLabel() {
				format.format(dateAndTime.getTime());
		        dateAndTimeLabel.setText(format.format(dateAndTime.getTime()));
		    }
		public static void updateTimeLabel(){
		        timeFormat.format(dateAndTime.getTime());
		        timeLabel.setText(timeFormat.format(dateAndTime.getTime()));
		}
	

}
