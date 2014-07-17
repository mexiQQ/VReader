package com.vreader.activity;

import com.example.vreader.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebviewActivity extends Activity {

	private WebView webview = null ;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		this.webview = (WebView) super.findViewById(R.id.webview) ;
		
		Bundle bundle = getIntent().getExtras();    
		//得到传过来的bundle    
		String data = bundle.getString("result");//读出数据   
		this.webview.loadUrl(data) ;
	}

}
