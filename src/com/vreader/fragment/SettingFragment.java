package com.vreader.fragment;


import com.example.vreader.R;
import com.vreader.utils.DBHelper;
import com.vreader.utils.DBHelperA;
import com.vreader.utils.DBHelperB;
import com.vreader.utils.DBHelperC;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



public class SettingFragment extends Fragment{
	DBHelper helper = null;
	DBHelperA helpera = null;
	DBHelperB helperb = null;
	DBHelperC helperc = null;
	private TextView tv_modify_pwd;
	//大字号字体  检查更新  清除缓存   信息推送  意见反馈
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		helper = new DBHelper(getActivity());
		helpera = new DBHelperA(getActivity());
		helperb = new DBHelperB(getActivity());
		helperc = new DBHelperC(getActivity());
		View view = inflater.inflate(R.layout.setting, container, false);
		tv_modify_pwd=(TextView) view.findViewById(R.id.tv_modify_pwd);
		tv_modify_pwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				helper.del();
				helpera.del();
				helperb.del();
				helperc.del();
				Toast.makeText(getActivity(), "清除缓存成功", Toast.LENGTH_LONG).show();
			}
		});
		return view;
		
	}
}
