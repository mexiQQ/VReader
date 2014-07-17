package com.vreader.fragment;


import cn.waps.AppConnect;

import com.example.vreader.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class OtherAppFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment, container, false);
		AppConnect.getInstance(getActivity()).showOffers(getActivity());
		return view;
	}
}
