package com.vreader.fragment;

import android.support.v4.app.Fragment;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.vreader.R;
import com.vreader.activity.NewsModuleActivity;
import com.vreader.activity.NewsLatestTextActivity;
import com.vreader.activity.NewsModuleTextActivity;
import com.vreader.domain.News;
import com.vreader.service.NewsService;
import com.vreader.utils.DBHelperA;
import com.vreader.utils.DBHelperB;
import com.vreader.utils.RefreshableView;
import com.vreader.utils.RefreshableView.PullToRefreshListener;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnItemSelectedListener,
		OnItemClickListener, OnScrollListener {
	public static final String MODULE_NUMBER = "module_number";
	List<String> title;
	List<String> content;
	List<HashMap<String, Object>> data;
	List<News> newslist = null;
	int currentpage = 1, lastItem, count, number = 1, visibleItemNumber = 0;

	ProgressDialog processBar;
	ListView listView;
	RefreshableView refreshableView;
	View rootView;
	SimpleAdapter adapter;

	static DBHelperB helper = null;

	int tag = 2;
	String tags = "test";

	public HomeFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (View) create(inflater, container);
		return rootView;
	}

	private View create(LayoutInflater inflater, ViewGroup container) {
		helper = new DBHelperB(getActivity().getApplicationContext());

		rootView = inflater.inflate(R.layout.home, container, false);

		listView = (ListView) rootView.findViewById(R.id.home_listView);
		refreshableView = (RefreshableView) rootView
				.findViewById(R.id.refreshable_view);

		listView.setOnItemClickListener(this);
		
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					getbankuai(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, tag);

		title = new ArrayList<String>();
		content = new ArrayList<String>();
		data = new ArrayList<HashMap<String, Object>>();

		getInfoFromCache(1);
		return rootView;
	}

	// *********************************************************************
	public void getbankuai(final int bankuai) {

		count = data.size();
		try {
			newslist = NewsService.getJsonFirstNews();
			// Log.v("test", newslist + "=newslist");
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
				values.put("content",URLDecoder.decode(news.getContent(), "gbk"));

				Log.v("test1", "content=" + content);
				helper.insert(values);
				data.add(item);
				Log.v("test1", data + "=data");

				title.add(URLDecoder.decode(news.getTitle(), "gbk"));
				content.add(URLDecoder.decode(news.getContent(), "gbk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendEmptyMessage(0);
	}

	public void getInfoFromCache(final int bankuai) {
		processBar = ProgressDialog.show(getActivity(), "", "正在加载");
		Cursor c = helper.query();
		if (c.getCount() > 0) {
			processBar.dismiss();
			String[] from = { "title", "time" };
			int[] to = { R.id.title, R.id.time };
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					getActivity(), R.layout.home_item, c, from, to);
			listView.setAdapter(adapter);
		} else {
			new Thread() {
				public void run() {
					getbankuai(tag);
				}
			}.start();
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (processBar.isShowing())
				processBar.dismiss();
			adddata();
		}
	};

	public void adddata() {
		Log.v("test", data + "=data2");
		adapter = new SimpleAdapter(getActivity(), data, R.layout.item,
				new String[] { "title", "time" }, new int[] { R.id.title,
						R.id.time });
		listView.setAdapter(adapter);
		listView.setSelection(count - visibleItemNumber);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 Intent it = new Intent(getActivity(), NewsLatestTextActivity.class);
		 TextView text1 = (TextView) arg1.findViewById(R.id.title);
		 String title = text1.getText().toString();
		 TextView text2 = (TextView) arg1.findViewById(R.id.time);
		 String time = text2.getText().toString();
		 it.putExtra("title", title);
		 it.putExtra("time", time);
		 startActivity(it);
	}
}
