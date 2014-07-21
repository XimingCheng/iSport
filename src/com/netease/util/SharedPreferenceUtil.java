package com.netease.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

	final static String flag="flag";
	
	private static SharedPreferences sp = null;

	public static void setSharedPreferences(SharedPreferences sps){
		sp = sps;
	}
	
	public static SharedPreferences getSharedPreferences() {
		return sp;
	}
	
	public static void saveAccount(String username, String location, float score,
			String completed_act_id, String uncompleted_act_id,
			String sex, String imageBase64, String label) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("location", location);
		editor.putFloat("score", score);
		editor.putString("completed_act_id", completed_act_id);
		editor.putString("uncompleted_act_id", uncompleted_act_id);
		editor.putString("sex", sex);
		editor.putString("imageBase64", imageBase64);
		editor.putString("label", label);
		editor.commit();
	}
	
	public static void setLogin(boolean bLogin) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("login", bLogin);
		editor.commit();
	}
	
	public static boolean isLogin() {
		return sp.getBoolean("login", false);
	}
}
