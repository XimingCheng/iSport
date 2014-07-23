package com.netease.isport;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class SplashActivity extends Activity{
    boolean isFirstIn = false;
    // Frame动画
    private AnimationDrawable animation;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 2500;
    private ImageView imgTween1,imgTween2,imgTween3; 
    private AnimationSet as,ns,js; 
    private Intent intent;
    private View view;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GO_HOME:
                goHome();
                break;
            case GO_GUIDE:
                goGuide();
                break;
            }
            super.handleMessage(msg);
        }
    };

    /** 初始化 */  
    public void init(int a,int b) { 
        // 声明AnimationSet  
    	intent=new Intent();
        float kuan=(float)a;
        float gao=(float)b;
    	as = new AnimationSet(true); 
    	ns=new AnimationSet(true);
    	js=new AnimationSet(true);
        AlphaAnimation aa = alphaAnim(0.5f, 1);
        TranslateAnimation ra = translateAnim(0, 0, -gao/2, gao/59); 
        TranslateAnimation ba = translateAnim(-kuan/2, 0f, 0f, 0f);
        TranslateAnimation ta = translateAnim(kuan/2, 0f, 0f, 0f);
        ns.addAnimation(ta);
        // 添加各种动画  
        as.addAnimation(ba); 
        js.addAnimation(ra);
        js.addAnimation(aa);
        imgTween1 = (ImageView) findViewById(R.id.imgTween1); 
        imgTween2=(ImageView) findViewById(R.id.imgTween2); 
        imgTween3=(ImageView)findViewById(R.id.imgTween3);
        imgTween2.startAnimation(ns); 
        imgTween1.startAnimation(as);
        imgTween3.startAnimation(js);
		
	}
   
	

    /** 透明度 */ 
    private AlphaAnimation alphaAnim(float x, float y) { 
        AlphaAnimation aa = new AlphaAnimation(x, y); 
        aa.setDuration(2000); 
        aa.setRepeatMode(Animation.REVERSE); 
        aa.setRepeatCount(0); 
        return aa; 
    } 
 
    /** 移动 */ 
    private TranslateAnimation translateAnim(float startX, float endX, 
            float startY, float endY) { 
        TranslateAnimation ta = new TranslateAnimation(startX, endX, startY, 
                endY); 
        ta.setDuration(2000); 
        ta.setRepeatMode(Animation.RESTART); 
        ta.setRepeatCount(0); 
        return ta; 
    } 
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash); 
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）

        init(width,height); 
       
        init();
        
    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        //isFirstIn = preferences.getBoolean("isFirstIn", true);
        isFirstIn=true;
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        }

    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }
}