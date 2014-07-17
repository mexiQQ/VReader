package com.vreader.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.vreader.domain.News;
import com.vreader.utils.ServerURL;
import com.vreader.utils.StreamTool;

import android.util.Log;


public class NewsService
{
	private static InputStream json;
	private static String path;
	private static int item,subitem;
	static int everypage=15;
	
	public static List<News> getJSONLastNews(int bankuai,int currentpage)
	{
		Log.v("test2","bankuai"+bankuai);
		switch(bankuai)
		{
			case 1:
				item=6;
				subitem=0;
				break;
			case 2:
				item=5;
				subitem=0;
				break;
			case 3:
				item=0;
				subitem=35;
				break;
			case 4:
				item=0;
				subitem=17;
				break;
			case 5:
				item=0;
				subitem=18;
				break;
			case 6:
				item=0;
				subitem=19;
				break;
		}
		path=ServerURL.serverURL+"android_news_findAllAndroid?itemID="+item+"&subItemID="+subitem+"&currentPage="+currentpage+"&everyPage="+everypage;
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200)
			{
				json = conn.getInputStream();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
 		return parseJSON(json);
	}
	
	public static List<News> getJsonFirstNews()
	{
		path=ServerURL.serverURL+"android_news_findAllAndroid?itemID="+6+"&subItemID="+0+"&currentPage="+1+"&everyPage="+everypage;
		//path=ServerURL.serverURL+"android_news_recentNewsListAndroid";
		Log.v("test",path+"=path");
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200)
			{
				json = conn.getInputStream();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		Log.v("test1",json+"=json");
 		return parseJSON(json);

	}
	
	public static List<News> getJsonInqueryNews(String key)
	{
		path=ServerURL.serverURL+"android_news_findNewsListByKeywords?keywords="+key;
		try
		{
			HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200)
			{
				json = conn.getInputStream();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
 		return parseJSON_NoContent(json);
	}
	
	
	private static List<News> parseJSON(InputStream jsonStream)
	{
		List<News> list = new ArrayList<News>();
		try
		{Log.v("test1",jsonStream+"=jsonStream");
			byte[] data = StreamTool.read(jsonStream);
			String json = new String(data);
			Log.v("test1",json+"=json");
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length() ; i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("id");
				String title = jsonObject.getString("title");
				String content = jsonObject.getString("content");
				String time = jsonObject.getString("time");
				//!!
				list.add(new News(id, title, content, time));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	private static List<News> parseJSON_NoContent(InputStream jsonStream)
	{
		List<News> list = new ArrayList<News>();
		try
		{
			byte[] data = StreamTool.read(jsonStream);
			String json = new String(data);
			JSONArray jsonArray = new JSONArray(json);
			for(int i = 0; i < jsonArray.length() ; i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				int id = jsonObject.getInt("id");
				String title = jsonObject.getString("title");
				String time = jsonObject.getString("time");
				//!!
				list.add(new News(id, title,"无内容", time));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
}
