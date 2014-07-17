package com.netease.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;

public class PostConnectionUtil {
	
	public final static String regUrl="http://efly.freeshell.ustc.edu.cn:54322/reg/";
	public final static String loginUrl="http://efly.freeshell.ustc.edu.cn:54322/login/";
	static List<NameValuePair> list=null;
	
	public static void setParm(List<NameValuePair> parm){
		list=parm;
	}
	
	public static HttpResponse postConnect(String url) throws URISyntaxException{
		HttpPost httpRequest=new HttpPost(new URI(url));
		HttpEntity httpentity = null;
        try {
            httpentity = new UrlEncodedFormEntity(list,"utf8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpRequest.setEntity(httpentity);
        HttpClient httpclient=new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpRequest);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return httpResponse;
	}
	
	public static int responseCode(HttpResponse httpResponse){
		return httpResponse.getStatusLine().getStatusCode();
	}
	
	public static String GetResponseMessage(HttpResponse httpResponse) {
		String message=null;
		try {
			message= EntityUtils.toString(httpResponse.getEntity());
		} catch (org.apache.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return message;
	}
}
