package com.vreader.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public final class HttpUtils {
	private static final String LOG_TAG = "HttpUtils Connect Tag";
	private static final int OPEN_URL_TIMEOUT = 10 * 1000;
	private static int openURLTimeout = OPEN_URL_TIMEOUT;
	private String url = "https://api.weibo.com/2/users/show.json";
	String returnJSON;
	public static void setOpenURLTimeout(int timeout) {
		openURLTimeout = timeout;
	}

	/**
	 * http
	 * 
	 * @param url
	 * @param method
	 * @param params
	 * @return
	 */
	public static String openUrl(String url, String method, Bundle params,
			String enc) {

		String response = null;

		if (method.equals("GET")) {
			url = url + "?" + encodeUrl(params);
			System.out.println(url);
			// url = url + "?"
			// +"uid=2236979104&access_token=2.00I9I58CB8lUJEa58da3e49e0kauwS";
		}
		try {
			Log.d(LOG_TAG, "Url:" + url);
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.setRequestProperty("User-Agent", System.getProperties()
					.getProperty("http.agent"));
			conn.setReadTimeout(openURLTimeout); // 璁剧疆瓒呮椂鏃堕棿

			if (method.equals("POST")) {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.getOutputStream().write(
						encodeUrl(params).getBytes("UTF-8"));
			}

			response = read(conn.getInputStream(), enc);
			conn.disconnect();
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * 
	 * @param in
	 * @param enc
	 * @return
	 * @throws IOException
	 */
	private static String read(InputStream in, String enc) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = null;
		if (enc != null) {
			// 鎸夋寚瀹氱殑缂栫爜璇诲叆娴�
			r = new BufferedReader(new InputStreamReader(in, enc), 1000);
		} else {
			// 鎸夐粯璁ょ殑缂栫爜璇诲叆
			r = new BufferedReader(new InputStreamReader(in), 1000);
		}

		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static String encodeUrl(Bundle parameters) {
		if (parameters == null)
			return "";
		StringBuilder sb = new StringBuilder();

		boolean first = true;
		/*
		 * for (String key : parameters.keySet()) { if (first) first = false;
		 * else sb.append("&"); sb.append(key + "="); String value =
		 * parameters.getString(key);
		 * 
		 * sb.append(value);
		 * 
		 * }
		 */

		for (String key : parameters.keySet()) {
			String value = parameters.getString(key);
			if(value==null)
				continue;
			if (first)
				first = false;
			else
				sb.append("&");
			sb.append(key + "=");
			
				sb.append(value);

		}

		return sb.toString();
	}
	public void asyncRequestAppVersion(final RequestListener listener,
			final String source, final String access_token, final String uid,
			final String screen_name,final Context context) {
		
		new AsyncTask<String, Void, String>() {
			protected String doInBackground(String... params) {

				try {
					 returnJSON = requestUserInfo(source, access_token,
							uid, screen_name);
					listener.onComplete(returnJSON,context);
				} catch (Exception e) {
					Log.e(LOG_TAG,
							"requestUserInfo Exception + " + e.getMessage());
					listener.onException(e);
				}

				return null;
			};
		}.execute();
		
	}

	public String requestUserInfo(String source, String access_token,
			String uid, String screen_name) {

		Bundle bundle = new Bundle();
		bundle.putString("source", source);
		bundle.putString("access_token", access_token);
		bundle.putString("uid", uid);
		bundle.putString("screen_name", screen_name);

		return openUrl(url, "GET", bundle, null);
	}
}
