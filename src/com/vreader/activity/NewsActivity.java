package com.vreader.activity;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.vreader.R;
import com.vreader.domain.News;
import com.vreader.service.NewsService;
import com.vreader.utils.DBHelperA;
import com.vreader.utils.RefreshableView;
import com.vreader.utils.RefreshableView.PullToRefreshListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/*
 * 新闻列表Activity
 */
public class NewsActivity extends Activity implements OnItemSelectedListener,
		OnItemClickListener {
	ListView listView;
	RefreshableView refreshableView;
	private LinearLayout linearLayout;
	SimpleAdapter adapter;
	float startY;
	private Thread verify;
	Bundle bundle;
	int currentpage = 1, lastItem, count, number = 1, visibleItemNumber = 0;
	int tag = 0;
	Boolean isshow = true;// 加载是否继续显示
	List<News> newslist = null;
	List<String> title;// 标题列表
	List<String> content;// 正文列表
	List<HashMap<String, Object>> data;
	static DBHelperA helper = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐去标题栏（应用程序的名字）
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news);

		ActionBar actionBar = this.getActionBar();    
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);    
		
		helper = new DBHelperA(getApplicationContext());
		title = new ArrayList<String>();
		content = new ArrayList<String>();
		data = new ArrayList<HashMap<String, Object>>();

		linearLayout = (LinearLayout) findViewById(R.id.linear);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);// 设置item单击事件

		bundle = getIntent().getExtras();
		tag = bundle.getInt("bankuai");
		switch (tag) {
		case 1:
			getInfoFromCache(1);
			break;
		case 2:
			getInfoFromCache(2);
			break;
		case 3:
			getInfoFromCache(3);
			break;
		case 4:
			getInfoFromCache(4);
			break;
		case 5:
			getInfoFromCache(5);
			break;
		case 6:
			getInfoFromCache(6);
			break;
		}
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					getbankuai(tag);
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, tag);
		
	}

	/**
	 * 用Handler来更新UI
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 添加数据
			adddata();
		}
	};

	public void adddata() {
		linearLayout.setVisibility(View.GONE);
		adapter = new SimpleAdapter(this, data, R.layout.item, new String[] {
				"title", "time" }, new int[] { R.id.title, R.id.time });
		listView.setAdapter(adapter);
		listView.setSelection(count - visibleItemNumber);
	}

	// *********************************************************************
	public void getbankuai(final int bankuai) {

		// 等待加载控件
		count = data.size();
		try {
			newslist = NewsService.getJSONLastNews(bankuai, currentpage);
			if (newslist == null) {
				number = 0;
			}
			ContentValues values = new ContentValues();
			for (News news : newslist) {
				HashMap<String, Object> item = new HashMap<String, Object>();

				item.put("id", news.getId());
				item.put("title", URLDecoder.decode(news.getTitle(), "gbk"));
				item.put("content", URLDecoder.decode(news.getContent(), "gbk"));
				item.put("time", URLDecoder.decode(news.getTime(), "gbk"));

				values.put("title", URLDecoder.decode(news.getTitle(), "gbk"));
				values.put("time", URLDecoder.decode(news.getTime(), "gbk"));
				values.put("content",
						URLDecoder.decode(news.getContent(), "gbk"));
				helper.insert(values);

				data.add(item);
				// 解码
				title.add(URLDecoder.decode(news.getTitle(), "gbk"));
				content.add(URLDecoder.decode(news.getContent(), "gbk"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendEmptyMessage(0);
	}

	// 从数据库曲信息
	public void getInfoFromCache(final int bankuai) {

		Cursor c = helper.query();
		if (c.getCount() > 0) {
			String[] from = { "title", "time" };
			int[] to = { R.id.title, R.id.time };
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
					R.layout.item, c, from, to);
			listView.setAdapter(adapter);
		} else {
			linearLayout.setVisibility(View.VISIBLE);
			verify = new Thread() {
				public void run() {
					getbankuai(tag);
				}
			};
			verify.start();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 Intent it = new Intent(NewsActivity.this, NewsTextActivity.class);
		 TextView text = (TextView) arg1.findViewById(R.id.title);
		 String tit = text.getText().toString();
		 it.putExtra("title", tit);
		 startActivity(it);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        getMenuInflater().inflate(R.menu.classify, menu);  
	        return true;  
	    }  
	  
	    @Override  
	    public boolean onOptionsItemSelected(MenuItem item) {  
	        switch (item.getItemId()) {  
	        case android.R.id.home:  
	            // 当ActionBar图标被点击时调用  
	        	finish();            
	            break;  
	        case R.id.menu_search:
	            
	        	break;
	        }  
	        return super.onOptionsItemSelected(item);  
	    }  
}
