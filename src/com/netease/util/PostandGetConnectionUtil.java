package com.netease.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import android.content.ContextWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import android.net.ParseException;
import android.widget.Toast;

public class PostandGetConnectionUtil {
	private static HttpClient httpclient = new DefaultHttpClient();
	public final static String regUrl="http://efly.freeshell.ustc.edu.cn:54322/reg/";
	public final static String loginUrl="http://efly.freeshell.ustc.edu.cn:54322/login/";
	public final static String logoutUrl = "http://efly.freeshell.ustc.edu.cn:54322/logout/";
	public final static String getinfoUrl = "http://efly.freeshell.ustc.edu.cn:54322/getinfo/";
	public final static String mediaUrlBase = "http://efly.freeshell.ustc.edu.cn:54322/media/";
	public final static String publicUrl="http://efly.freeshell.ustc.edu.cn:54322/public/";
	public final static String editsexUrl = "http://efly.freeshell.ustc.edu.cn:54322/edit_sex/";
	public final static String editlabelUrl = "http://efly.freeshell.ustc.edu.cn:54322/edit_label/";
	public final static String uploadUrl = "http://efly.freeshell.ustc.edu.cn:54322/photo_upload/";
	public final static String searchUrl = "http://efly.freeshell.ustc.edu.cn:54322/query/";
	
	static List<NameValuePair> list=null;
	
	public static void setParm(List<NameValuePair> parm){
		list = parm;
	}
	
	public static HttpResponse getConnect(String url,List<NameValuePair> list) throws URISyntaxException {
		for(int i=0;i<list.size();i++){
			NameValuePair pair=list.get(i);
			if(i<list.size()-1){
			   url+=pair.getName()+"="+pair.getValue()+"&";
			}
			else{
				url+=pair.getName()+"="+pair.getValue();
			}
		}
		return getConnect(url);
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
	
	public static HttpResponse postFile(String url, String image_path) throws ClientProtocolException, IOException {
		HttpResponse response = null;
		FileBody bin = null;
		HttpPost httppost = new HttpPost(url);
		File file = new File(image_path);
		if(file.length()>819200)
			return response;
        if(file != null) {
            bin = new FileBody(file);
        }
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("photo", bin);
        httppost.setEntity(reqEntity);
        response = httpclient.execute(httppost);
		return response;
	}
	
	public static HttpResponse postConnect(String url) throws URISyntaxException {
		HttpPost httpRequest = new HttpPost(new URI(url));
		if(list != null && list.size() > 0) {
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
