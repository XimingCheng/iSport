package com.netease.isport;

import com.netease.isport.R;
import android.app.Activity;
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

public class MainActivity extends Activity {
	private SlideMenu mSlideMenu;
	private LinearLayout mUserProfileLayout;
	private ImageView mUserImage;
	private TextView  option1;

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
		
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mUserProfileLayout = (LinearLayout) findViewById(R.id.user_image_layout);
		mUserImage = (ImageView) findViewById(R.id.user_image);
		
		option1   = (TextView) findViewById(R.id.option_submit_act);
		
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
		Bitmap output = toRoundCorner(bitmap);
		mUserImage.setImageBitmap(output);
		menuImg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
					if (mSlideMenu.isMainScreenShowing()) {
						mSlideMenu.openMenu();
					} else {
						mSlideMenu.closeMenu();
					}
			}
		});
		
		option1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!mSlideMenu.isMainScreenShowing()) {
					mSlideMenu.closeMenu();
				}
			}
		});
		
		mUserProfileLayout.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if (!mSlideMenu.isMainScreenShowing()) {
					mSlideMenu.closeMenu();
				}
			}
		});
	}

	

}
