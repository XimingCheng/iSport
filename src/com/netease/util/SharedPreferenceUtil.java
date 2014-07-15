package com.netease.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

	final static String flag="flag";
	
	private static SharedPreferences sp=null;

	public static void setSharedPreferences(SharedPreferences sps){
		sp=sps;
	}
	
	public static void saveAccount(String username,String password){
		SharedPreferences.Editor editor = sp.edit(); 
		editor.putBoolean(flag, true);
	}
	
	public static boolean getAccount(){
		 return sp.getBoolean(flag, false);
	}
   
   
   
}
