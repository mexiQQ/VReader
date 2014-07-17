package com.vreader.fragment;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.vreader.R;
import com.vreader.domain.News;
import com.vreader.service.NewsService;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchFragment extends Fragment implements OnItemSelectedListener,
		OnItemClickListener, OnScrollListener {
	public static final String MODULE_NUMBER = "module_number";
	List<String> title;// �����б�
	List<String> content;// �����б�
	List<HashMap<String, Object>> data;
	List<News> newslist = null;
	int currentpage = 1, lastItem, count, number = 1, visibleItemNumber = 0;

	ImageButton imageButton;
	View rootView;
	ListView listView;
	ProgressDialog processBar;
	SimpleAdapter adapter;
	EditText edit;

	String tag = "test";
	Editable key;

	public SearchFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		int i = getArguments().getInt(MODULE_NUMBER);
//		String planet = getResources().getStringArray(R.array.Titles)[i];

		rootView = (View) create(inflater, container);
//		getActivity().setTitle(planet);
		return rootView;
	}

	private View create(LayoutInflater inflater, ViewGroup container) {
		rootView = inflater.inflate(R.layout.search, container, false);

		listView = (ListView) rootView.findViewById(R.id.search_listView);
		imageButton = (ImageButton) rootView.findViewById(R.id.search_button);
		edit = (EditText) rootView.findViewById(R.id.editview);
		key = edit.getText();

		listView.setOnItemClickListener(this);// 
		imageButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				getbankuai(key.toString());
			}
		});

		title = new ArrayList<String>();
		content = new ArrayList<String>();
		data = new ArrayList<HashMap<String, Object>>();

		return rootView;
	}

	public void getbankuai(final String key) {
		processBar = ProgressDialog.show(getActivity(), "", "正在查询");
		new Thread() {
			public void run() {
				try {
					newslist = NewsService.getJsonInqueryNews(key);
					if (newslist == null) {
						Log.v(tag, "it's here1");
						number = 0;
					}
					for (News news : newslist) {
						HashMap<String, Object> item = new HashMap<String, Object>();
						item.put("id", news.getId());
						item.put("title",
								URLDecoder.decode(news.getTitle(), "gbk"));
						item.put("content",
								URLDecoder.decode(news.getContent(), "gbk"));
						item.put("time",
								URLDecoder.decode(news.getTime(), "gbk"));
						Log.v(tag, data + "=data3");
						data.add(item);
						Log.v(tag, data + "=data1");
						// ����
						title.add(URLDecoder.decode(news.getTitle(), "gbk"));
						content.add(URLDecoder.decode(news.getContent(), "gbk"));
					}
				} catch (Exception e) {
					Log.v(tag, "it's here2");
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// �رյȴ����
			processBar.dismiss();
			// ������
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
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
