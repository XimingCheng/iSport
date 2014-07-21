package com.netease.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;

public class PostandGetConnectionUtil {
	private static HttpClient httpclient = new DefaultHttpClient();
	public final static String regUrl="http://efly.freeshell.ustc.edu.cn:54322/reg/";
	public final static String loginUrl="http://efly.freeshell.ustc.edu.cn:54322/login/";
	public final static String logoutUrl = "http://efly.freeshell.ustc.edu.cn:54322/logout/";
	public final static String getinfoUrl = "http://efly.freeshell.ustc.edu.cn:54322/getinfo/";
	public final static String mediaUrlBase = "http://efly.freeshell.ustc.edu.cn:54322/media/";
	
	static List<NameValuePair> list=null;
	
	public static void setParm(List<NameValuePair> parm){
		list=parm;
	}
	
	public static HttpResponse getConnect(String url) throws URISyntaxException {
		HttpGet httpRequest = new HttpGet(new URI(url));
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
	
	public static HttpResponse postConnect(String url) throws URISyntaxException {
		HttpPost httpRequest = new HttpPost(new URI(url));
		if(list != null) {
			HttpEntity httpentity = null;
	        try {
	            httpentity = new UrlEncodedFormEntity(list,"utf8");
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        httpRequest.setEntity(httpentity);
		}
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
