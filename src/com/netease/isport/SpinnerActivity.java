package com.netease.isport;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerActivity extends Activity  {

	 private static final String[] m={"A型","B型","O型","AB型","其他"};
	    private TextView view ;
	    private Spinner spinner;
	    private ArrayAdapter<String> adapter;
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_login);
		//	view = (TextView) findViewById(R.id.spanner1);
			
			//view.setOnClickListener(this);
			//textView.setOnClickListener(this);
			//SharedPreferences sp=getSharedPreferences("test", 0);
			
	         
	    }
	     
	    //使用数组形式操作
	    class SpinnerSelectedListener implements OnItemSelectedListener{
	 
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	            view.setText("你的血型是："+m[arg2]);
	        }
	 
	        public void onNothingSelected(AdapterView<?> arg0) {
	        }
	    }
	
}
