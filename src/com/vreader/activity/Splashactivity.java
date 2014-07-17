package com.vreader.activity;


import com.example.vreader.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.os.Handler;
public class Splashactivity extends Activity {
	private ImageView splash = null;
	private final int SPLASH_DISPLAY_LENGHT = 8000; // 延迟八秒
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		  /*new Handler().postDelayed(new Runnable() {
		   public void run() {
		    Intent mainIntent = new Intent(Splashactivity.this,
		      MainActivity.class);
		    Splashactivity.this.startActivity(mainIntent);
		    Splashactivity.this.finish();
		   }

		  }, SPLASH_DISPLAY_LENGHT);*/

		this.splash = (ImageView) super.findViewById(R.id.splash);
		AnimationSet set = new AnimationSet(true);		// 定义一个动画集
		ScaleAnimation scale = new ScaleAnimation(
				1, 1.1f, 								// X轴从满屏缩小到无
				1, 1.1f,								// Y轴从满屏缩小到无 
				Animation.RELATIVE_TO_SELF, 0.5f,		// 以自身0.5宽度为轴缩放
				Animation.RELATIVE_TO_SELF, 0.5f); 		// 以自身0.5高度为轴缩放
		//AlphaAnimation alpha = new AlphaAnimation(1, 0.6f);	// 完全显示-->完全透明
		//set.addAnimation(alpha) ;	
		set.addAnimation(scale) ;		
		set.setDuration(2400) ;
		this.splash.startAnimation(set) ;	// 启动动画
		
		new Thread() {

			@Override
			public void run() {
				super.run();
				try {
					sleep(2000);
					Intent intent = new Intent(Splashactivity.this,MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();

		
		
		
		
		
		
		
	}

}
