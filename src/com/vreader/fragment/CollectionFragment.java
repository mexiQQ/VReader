package com.vreader.fragment;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.vreader.R;
import com.vreader.activity.NewsCollectTextActivity;
import com.vreader.activity.NewsModuleTextActivity;
import com.vreader.utils.DBHelperC;
import com.vreader.utils.RefreshableView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

public class CollectionFragment extends Fragment implements OnItemSelectedListener,
		OnItemClickListener, OnScrollListener {
	List<String> title;
	List<String> content;
	List<HashMap<String, Object>> data;
	int  count,  visibleItemNumber = 0;

	ProgressDialog processBar;
	ListView listView;
	RefreshableView refreshableView;
	View rootView;
	SimpleAdapter adapter;

	static DBHelperC helper = null;


	public CollectionFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (View) create(inflater, container);
		return rootView;
	}

	private View create(LayoutInflater inflater, ViewGroup container) {
		helper = new DBHelperC(getActivity().getApplicationContext());

		rootView = inflater.inflate(R.layout.collection, container, false);

		listView = (ListView) rootView.findViewById(R.id.collect_listView);

		listView.setOnItemClickListener(this);

		title = new ArrayList<String>();
		content = new ArrayList<String>();
		data = new ArrayList<HashMap<String, Object>>();

		getInfoFromCache(1);
		return rootView;
	}

	
	public void getInfoFromCache(final int bankuai) {
		processBar = ProgressDialog.show(getActivity(), "", "正在加载");
		Cursor c = helper.query();
		if (c.getCount() > 0) {
			processBar.dismiss();
			String[] from = { "title", "time" };
			int[] to = { R.id.title, R.id.time };
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
					R.layout.home_item, c, from, to);
			listView.setAdapter(adapter);
		} else
		{
			processBar.dismiss();
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
		Intent it = new Intent(getActivity(), NewsCollectTextActivity.class);
		 TextView text = (TextView) arg1.findViewById(R.id.title);
		 String tit = text.getText().toString();
		 it.putExtra("title", tit);
		 startActivity(it);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent it = new Intent(getActivity(), NewsCollectTextActivity.class);
		 TextView text = (TextView) arg1.findViewById(R.id.title);
		 String tit = text.getText().toString();
		 it.putExtra("title", tit);
		 startActivity(it);
	}
}
