package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.netease.util.GetIntentInstance;
import com.netease.util.MyAdapter;

public class SearchActivity extends Activity implements OnClickListener{
   // Button button=null;
    private TextView view ;
    private ImageView imageView;
    private Button button;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		spinner = (Spinner) findViewById(R.id.spinner1);
		button=(Button) findViewById(R.id.search);
		imageView=(ImageView) findViewById(R.id.title_bar_menu_btn);
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
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubb
		Intent intent=GetIntentInstance.getIntent();
		switch(v.getId()) {
			case R.id.search :{
				//ËÑË÷ÒµÎñÂß¼­
				break;
			}
			case R.id.title_bar_menu_btn : {
				intent.setClass(SearchActivity.this,MainActivity.class);
				startActivity(intent); break;
			}
			
		}
		
	}
}
