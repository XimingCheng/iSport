package com.netease.isport;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
	 
	public class MyArrayAdapter extends ArrayAdapter<Student> {
	
		Context context;
			    int layoutResourceId;
			    ArrayList<Student> students = new ArrayList<Student>();
			 
			    public MyArrayAdapter(Context context, int layoutResourceId,
			            ArrayList<Student> studs) {
			        super(context, layoutResourceId, studs);
			        this.layoutResourceId = layoutResourceId;
			        this.context = context;
			        this.students = studs;
			    }
			 
			    @Override
			    public View getView(int position, View convertView, ViewGroup parent) {
			        View item = convertView;
			        StudentWrapper StudentWrapper = null;
			 
			        if (item == null) {
			            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			            item = inflater.inflate(layoutResourceId, parent, false);
			            StudentWrapper = new StudentWrapper();
			            StudentWrapper.info = (TextView) item.findViewById(R.id.text1);
			            StudentWrapper.time = (TextView) item.findViewById(R.id.textName);
			            StudentWrapper.bilv = (TextView) item.findViewById(R.id.textAge);
			            StudentWrapper.button1 = (Button) item.findViewById(R.id.button1);
			           
		            item.setTag(StudentWrapper);
			        } else {
			            StudentWrapper = (StudentWrapper) item.getTag();
			        }
			 
			        Student student = students.get(position);
			        StudentWrapper.info.setText(student.getinfo());
			        StudentWrapper.time.setText(student.gettime());
			        StudentWrapper.bilv.setText(student.getbilv());
			 
			        StudentWrapper.button1.setOnClickListener(new OnClickListener() {
			 
			            @Override
			            public void onClick(View v) {
			                Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
			            }
			        });
			 
		       StudentWrapper.info.setOnClickListener(new OnClickListener() {
			 
			            @Override
		            public void onClick(View v) {
		 
		               Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show();
		            }
		        });
		 
			        return item;
			 
			    }
		 
		    static class StudentWrapper {
			        TextView info;
			        TextView time;
			        TextView bilv;
		        Button button1;
			       
		   }
 
}
