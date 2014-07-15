package com.netease.isport;

import com.netease.isport.R;
import com.netease.util.SharedPreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  implements OnClickListener {
	private SlideMenu mSlideMenu;
	private LinearLayout mUserProfileLayout;
	private ImageView mUserImage;
	private TextView  option1;
	SharedPreferences sp;

	public static Bitmap toRoundCorner(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getHeight(),bitmap.getWidth(),Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        RectF rectF = new RectF(rect);
        
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect,rectF, paint);
        return output;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    sp=getSharedPreferences("setting", 0);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mUserProfileLayout = (LinearLayout) findViewById(R.id.user_image_layout);
		mUserImage = (ImageView) findViewById(R.id.user_image);
		
		option1   = (TextView) findViewById(R.id.option_submit_act);
		
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
		Bitmap output = toRoundCorner(bitmap);
		mUserImage.setImageBitmap(output);
		menuImg.setOnClickListener(this);
		
		option1.setOnClickListener(this);
		
		mUserProfileLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		   if(sp.getAll().isEmpty()||(!SharedPreferenceUtil.getAccount())){
		    	Intent intent=new Intent();
		    	intent.setClass(MainActivity.this, LoginActivity.class);
		    	startActivity(intent);
		    }
		   else{
			   switch(v.getId()){
			     case R.id.slide_menu:{
			    	 if(mSlideMenu.isMainScreenShowing()){
					    	mSlideMenu.openMenu();
					   } else {
						   mSlideMenu.closeMenu();
					  }
			     }
			     case R.id.option_submit_act :{
			    	 if (!mSlideMenu.isMainScreenShowing()) {
							mSlideMenu.closeMenu();
						}
			     }
			     
			     case R.id.user_image_layout:{
			    	 if (!mSlideMenu.isMainScreenShowing()) {
							mSlideMenu.closeMenu();
						}
			     }
			   }
			   
		   }
		   
		
	}

	

}
