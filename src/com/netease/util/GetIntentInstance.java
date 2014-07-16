package com.netease.util;

import android.content.Intent;

public class GetIntentInstance {
    private static final Intent intent=new Intent();
	public static Intent getIntent(){
	    return intent;
   }
	

}
