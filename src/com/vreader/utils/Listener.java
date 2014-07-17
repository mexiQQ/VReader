package com.vreader.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import com.vreader.domain.User;
import com.vreader.keep.UserInfoKeeper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Listener implements RequestListener {
	SharedPreferences share=null;
	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
	@Override
	public void onComplete(String result,Context context) {
		Log.v("test",result+"=result");
		User user=parseJSON(result);
		Log.v("test",user+"=user");
		UserInfoKeeper.writeUser(context, user);
		Log.v("test1",user+"=user");
	}

	@Override
	public void onException(Exception e) {
		System.out.println(e + "这里显示错误原因");
	}
	
	
	/**
	 * 通过url 获得对应的Drawable资源
	 * 
	 * @param url
	 * @return
	 */
	public static Drawable getDrawableFromUrl(String url) {
		try {
			URLConnection urls = new URL(url).openConnection();
			return Drawable.createFromStream(urls.getInputStream(), "image");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static User parseJSON(String json)
	{
		User user=null;
		try
		{
		    JSONObject jsonObject=new JSONObject(json);
			String userid = jsonObject.getString("id");
			String username = jsonObject.getString("name");
			String userPictureUrl = jsonObject.getString("profile_image_url");
			user=new User(username, userid, userPictureUrl);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return user;
	}
}
