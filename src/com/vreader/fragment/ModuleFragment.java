package com.vreader.fragment;

import com.example.vreader.R;
import com.vreader.activity.NewsModuleActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/*
 * 新闻版块Activity
 */
public class ModuleFragment extends Fragment {
	public static final String MODULE_NUMBER = "module_number";

	public ModuleFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// int i = getArguments().getInt(MODULE_NUMBER);
		// String planet = getResources().getStringArray(R.array.Titles)[i];

		View rootView = (View) create(1, inflater, container);

		// getActivity().setTitle(planet);
		return rootView;
	}

	private View create(int i, LayoutInflater inflater, ViewGroup container) {
		View rootView;
		switch (i) {
		case 1:
			rootView = inflater.inflate(R.layout.module, container,
					false);

			ImageButton b1,b2,b3,b4,b5,b6;
		
			b1 = (ImageButton) rootView.findViewById(R.id.imageButton1);
			b2 = (ImageButton) rootView.findViewById(R.id.imageButton2);
			b3 = (ImageButton) rootView.findViewById(R.id.imageButton3);
			b4 = (ImageButton) rootView.findViewById(R.id.imageButton4);
			b5 = (ImageButton) rootView.findViewById(R.id.imageButton5);
			b6 = (ImageButton) rootView.findViewById(R.id.imageButton6);
			
			b1.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 通知公告
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 1);
					startActivity(it);
				}
			});
			b2.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 新闻资讯
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 2);
					startActivity(it);
				}
			});
			b3.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 院系交流
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 3);
					startActivity(it);
				}
			});
			b4.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 校园招聘
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 4);
					startActivity(it);
				}
			});
			b5.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 在线招聘
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 5);
					startActivity(it);
				}
			});
			b6.setOnClickListener(new ImageButton.OnClickListener() {
				public void onClick(View v) {
					// 实习招聘
					Intent it = new Intent(getActivity(), NewsModuleActivity.class);
					it.putExtra("bankuai", 6);
					startActivity(it);
				}
			});
			return rootView;

		default:
			break;
		}
		return null;
	}
}
