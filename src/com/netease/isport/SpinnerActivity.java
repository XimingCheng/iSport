package com.netease.isport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.util.MyAdapter;

public class SpinnerActivity extends Activity  {

	
	    private TextView view ;
	    private Spinner spinner;
	    private ArrayAdapter<String> adapter;
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_search);
			spinner = (Spinner) findViewById(R.id.spinner1);

			String[] mItems = getResources().getStringArray(R.array.planets_arry);
			MyAdapter _Adapter=new MyAdapter(this,mItems);
			spinner.setAdapter(_Adapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				
		    		@Override
		    		public void onItemSelected(AdapterView<?> parent, View view,
		    				int position, long id) {
		    			   String str=parent.getItemAtPosition(position).toString();
		    		}
		    		@Override
		    		public void onNothingSelected(AdapterView<?> parent) {
		    			// TODO Auto-generated method stub
		    		}
		    	});

			//	view = (TextView) findViewById(R.id.spanner1);
			
			//view.setOnClickListener(this);
			//textView.setOnClickListener(this);
			//SharedPreferences sp=getSharedPreferences("test", 0);
			
	         
	    }
	     
	    
	
}
