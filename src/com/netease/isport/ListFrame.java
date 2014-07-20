package com.netease.isport;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ListFrame extends FrameLayout {
	/**
	 * 手指按下X的坐标
	 */
	private int downY;
	/**
	 * 手指按下Y的坐标
	 */
	private int downX;
	private boolean isSlide = false;
	public ListFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 处理我们拖动ListView item的逻辑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			System.out.println("子类点击onTouchEvent");
			break;
		case MotionEvent.ACTION_MOVE: 
			System.out.println("子类滑动onTouchEvent");
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("子类放开onTouchEvent");
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			System.out.println("子类点击dispatchTouchEvent");
			break;
		case MotionEvent.ACTION_MOVE: 
			System.out.println("子类滑动dispatchTouchEvent");
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("子类放开dispatchTouchEvent");
			break;
		}
		return super.dispatchTouchEvent(event);
	}
}
