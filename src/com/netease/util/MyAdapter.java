package com.netease.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netease.isport.R;

public class MyAdapter extends BaseAdapter {
   Context mContext=null;
	
	String[] name=new String[12];
	public MyAdapter(Context pContext,String[] args){
		this.mContext=pContext;
		this.name=args;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return name.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return name[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
		        convertView=_LayoutInflater.inflate(R.layout.item, null);
		        if(convertView!=null)
		        {
		            TextView _TextView1=(TextView)convertView.findViewById(R.id.EditText1);    
		            _TextView1.setText(name[position]);
		        }
		        return convertView;
		    }
	}


