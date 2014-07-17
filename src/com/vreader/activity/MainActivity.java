package com.vreader.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.vreader.R;
import com.vreader.fragment.*;
import com.vreader.utils.UpdateManager;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
		implements
			NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private List<Fragment> lists;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private TextView tvTitle;
	private MabDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private ImageButton left;
	private ImageButton right;
	
	AlertDialog.Builder builder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();

		setContentView(R.layout.activity_main);

		builder = new Builder(this);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// ********************************************************************************
		// 在此处添加自定义actionbar
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, Gravity.LEFT);
		View viewTitleBar = getLayoutInflater().inflate(
				R.layout.action_bar_title, null);
		getActionBar().setCustomView(viewTitleBar, lp);
		//getActionBar().setCustomView(viewTitleBar);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setDisplayShowCustomEnabled(true);
		
		tvTitle = (TextView) getActionBar().getCustomView().findViewById(
				android.R.id.title);
		tvTitle.setText(mTitle);
		left = (ImageButton) getActionBar().getCustomView()
				.findViewById(R.id.left_btn);
		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerToggle.switchDrawer();
			}
		});
		right = (ImageButton) getActionBar().getCustomView()
				.findViewById(R.id.login_btn);
		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		mDrawerToggle = new MabDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				);
				mDrawerLayout.setDrawerListener(mDrawerToggle);
				if (savedInstanceState == null) {
					
				}
				

	}

	
	
	private class MabDrawerToggle extends ActionBarDrawerToggle{

		public MabDrawerToggle(Activity activity, DrawerLayout drawerLayout,
				int drawerImageRes, int openDrawerContentDescRes,
				int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
					closeDrawerContentDescRes);
			// TODO Auto-generated constructor stub
		}
		public void switchDrawer() 
		{ 
		    if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) 
		    { 
		        mDrawerLayout.closeDrawer(GravityCompat.START); 
		        tvTitle.setText(mTitle);
		    } 
		    else
		    { 
		        mDrawerLayout.openDrawer(GravityCompat.START); 
		        tvTitle.setText(R.string.app_name);
		    } 
		}
		
	}
	
	public void init() {
		lists = new ArrayList<Fragment>();
		Fragment fg1 = new HomeFragment();
		Fragment fg2 = new ModuleFragment();
		Fragment fg3 = new SearchFragment();
		Fragment fg4 = new CollectionFragment();
		Fragment fg5 = new SettingFragment();
		Fragment fg6 = new AboutFragement();
		lists.add(fg1);
		lists.add(fg2);
		lists.add(fg3);
		lists.add(fg4);
		lists.add(fg5);
		lists.add(fg6);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {

		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction()
				.replace(R.id.container, lists.get(position)).commit();
	}


	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			builder.setTitle("警告警告出现啦")
					.setIcon(R.drawable.ic_launcher)
					.setMessage("您确定要退出系统吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setNegativeButton("取消", null);
			builder.create().show();
		}

		return false;

	}
}
