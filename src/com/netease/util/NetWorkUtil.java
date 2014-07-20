package com.netease.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

	public static boolean isNetworkConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
			.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	}
}
