package com.netease.isport;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.common.reflect.TypeToken;

public class DecodeJson {

	public JsonInfoResult jsonInfo(String result) {
		Type type = new TypeToken<JsonInfoResult>(){}.getType();  
	    Gson gson = new Gson();  
	    JsonInfoResult b = gson.fromJson(result, type);
	    return b;
	}
	
	public JsonPushRet jsonPush(String result) {
		Type type = new TypeToken<JsonPushRet>(){}.getType();  
	    Gson gson = new Gson();  
	    JsonPushRet b = gson.fromJson(result, type);
	    return b;
	}
	
	public JsonRet jsonRet(String result) {
		Type type = new TypeToken<JsonRet>(){}.getType();  
	    Gson gson = new Gson();  
	    JsonRet b = gson.fromJson(result, type);
	    return b;
	}
}
