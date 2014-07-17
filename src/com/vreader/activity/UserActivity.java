package com.vreader.activity;

import com.example.vreader.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.vreader.domain.User;
import com.vreader.keep.AccessTokenKeeper;
import com.vreader.keep.UserInfoKeeper;
import com.vreader.utils.HttpUtils;
import com.vreader.utils.Listener;
import com.vreader.utils.RequestListener;
import com.vreader.utils.UpdateManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UserActivity extends Activity implements OnClickListener {
	private WeiboAuth mWeiboAuth;
	private Oauth2AccessToken mAccessToken;
	private static final String CONSUMER_KEY = "1531382308";// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://www.mexiqq.com";
	private static final String SCOPE = "";
	private RelativeLayout sinaLogin = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		mWeiboAuth = new WeiboAuth(this, CONSUMER_KEY, REDIRECT_URL, SCOPE);
		sinaLogin = (RelativeLayout) this.findViewById(R.id.sina_weibo_login);
		sinaLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sina_weibo_login:
			
			mWeiboAuth.anthorize(new AuthListener());
			break;

		default:
			break;
		}

	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);

			String token = mAccessToken.getToken();
			String uid = mAccessToken.getUid();

			if (mAccessToken.isSessionValid()) {

				Toast.makeText(getApplicationContext(), "认证成功",
						Toast.LENGTH_LONG).show();

				AccessTokenKeeper.writeAccessToken(UserActivity.this,
						mAccessToken);
				RequestListener listener = new Listener();
				HttpUtils httpUtils = new HttpUtils();

				// 其中screen_name 与 uid 必须有一个传空值

				httpUtils.asyncRequestAppVersion(listener, null, token, uid,
						null, UserActivity.this);
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(1500);
							 Intent intent=new Intent(UserActivity.this,
							 MainActivity.class);
							 startActivity(intent);
							finish();
						} catch (Exception e) {

						}
					}
				}.start();

			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");

				Toast.makeText(getApplicationContext(), "认证返回Code",
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "认证取消", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			Toast.makeText(getApplicationContext(),
					"认证异常 : " + arg0.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	// 重写返回键
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
					Intent intent=new Intent(UserActivity.this,
							 MainActivity.class);
							 startActivity(intent);
							finish();
					return true;
				}
				return super.onKeyDown(keyCode, event);
			}
}
