package com.vreader.utils;

import android.content.Context;

public interface RequestListener {
	//成功结束时，触发
	public void onComplete(String result,Context context); 
	//出现异常时触发
	public void onException(Exception e);
}
