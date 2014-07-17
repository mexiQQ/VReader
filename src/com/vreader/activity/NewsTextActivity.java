package com.vreader.activity;


import com.example.vreader.R;
import com.vreader.utils.DBHelperA;
import com.vreader.utils.ServerURL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.text.Html;
//import android.text.method.ScrollingMovementMethod;
//import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.SimpleCursorAdapter;
//import android.widget.TextView;
/*
 * 详细新闻Activity
 */
@SuppressWarnings("deprecation")
public class NewsTextActivity extends Activity
{
	DBHelperA helper = null;
	private int font,on_off;
	String title=null;
	String content=null;
	int _id=0;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //隐去标题栏（应用程序的名字）
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.newstext);
        
        ActionBar actionBar = this.getActionBar();    
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);    
        
        helper = new DBHelperA(getApplicationContext());
        
        WebView webView = (WebView) findViewById(R.id.webView01);
        WebSettings websetting=webView.getSettings();
        //TextView biaoti=(TextView)findViewById(R.id.textView1);
        //TextView zhengwen=(TextView)findViewById(R.id.textView2);
        //设置存储信号对，（后边默认）
		SharedPreferences sharedPreferences = getSharedPreferences("setup", Activity.MODE_PRIVATE);
		font=sharedPreferences.getInt("font", 1);
		on_off=sharedPreferences.getInt("on_off", 0);
		
		Bundle bundle=getIntent().getExtras();
		title =bundle.getString("title");
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("Out", new String[]{"content"}, "title=?", new String[]{title}, null, null, null);
		if (c.moveToNext()) {
			//_id=c.getInt(c.getColumnIndex("_id"));
			//title = c.getString(c.getColumnIndex("_id"));
			content = c.getString(c.getColumnIndex("content"));
			
		}
        switch(font)
        {
        	case 0:
        		webView.getSettings().setTextSize(TextSize.LARGER);
        		//zhengwen.setTextSize(30);
        		break;
        	case 1:
        		webView.getSettings().setTextSize(TextSize.NORMAL);
        		//zhengwen.setTextSize(20);
        		break;
        	case 2:
        		webView.getSettings().setTextSize(TextSize.SMALLER);
        		//zhengwen.setTextSize(10);
        		break;
        }
        
        //biaoti.setText(bundle.getString("title"));
        //zhengwen.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        if(on_off!=0)
        {
        	//zhengwen.setText(Html.fromHtml(bundle.getString("title")+bundle.getString("content")));
        	//zhengwen.setVisibility(View.VISIBLE);
        	//加载图像
            websetting.setBlockNetworkImage(true);
            webView.loadDataWithBaseURL(ServerURL.serverURL, title+content, "text/html", "utf-8", null);
        }
        else
        {
        	//不加载图�?
            websetting.setBlockNetworkImage(false);
        	webView.loadDataWithBaseURL(ServerURL.serverURL, title+content, "text/html", "utf-8", null);
        	//zhengwen.setVisibility(View.INVISIBLE);
        }
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        getMenuInflater().inflate(R.menu.main, menu);  
        return true;  
    }  
  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case android.R.id.home:  
            // 当ActionBar图标被点击时调用  
        	finish();            
            break;  
        case R.id.menu_add:
        
        	break;
        case R.id.menu_collect:
            
        	break;
        }  
        return super.onOptionsItemSelected(item);  
    }  
}
