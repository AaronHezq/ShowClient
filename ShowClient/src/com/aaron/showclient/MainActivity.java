package com.aaron.showclient;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.aaron.showclient.view.MyAnimation1;
import com.aaron.showclient.view.MyAnimation2;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class MainActivity extends Activity {

	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	private ImageView imageView;

	private SlidingMenu menu;

	private ToggleButton toggleButton;

	private int position = 0;

	private boolean isRun = true;

	private String[] imgs = new String[] { "drawable://" + R.drawable.test1, "drawable://" + R.drawable.test2,
			"drawable://" + R.drawable.test3, "drawable://" + R.drawable.test4, "drawable://" + R.drawable.test5,
			"drawable://" + R.drawable.test6 };

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (isRun) {
					position ++;
					if (position >= imgs.length) {
						position = 0;
					}
					loadImg();
				}
				sendMsg();
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initImageLoader(this);
		options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
				.cacheInMemory(true).cacheOnDisc(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageView = (ImageView) findViewById(R.id.imageview);
		loadImg();
		sendMsg();
		initMenu();
	}
	
	public void loadImg() {
		imageLoader.displayImage(imgs[position], imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				startAnim((ImageView) view, loadedImage, position);
				super.onLoadingComplete(imageUri, view, loadedImage);
			}
		});
	}

	public void sendMsg() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		}, 4000);
	}

	public void initMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
		toggleButton = (ToggleButton) menu.findViewById(R.id.menu_togglebutton);
		toggleButton.setOnToggleChanged(new OnToggleChanged() {
			@Override
			public void onToggle(boolean on) {
				if (on) {
					System.out.println("打开");
				} else {
					System.out.println("关闭");
				}
			}
		});
	}

	public void startAnim(ImageView imageView, Bitmap bitmap, int position) {
		switch (position) {
		case 0:
			TranslateAnimation translateAnimation = new TranslateAnimation(bitmap.getWidth(), 0, bitmap.getHeight(), 0);
			translateAnimation.setDuration(1500);
			imageView.setImageBitmap(bitmap);
			imageView.startAnimation(translateAnimation);
			break;
		case 1:
			ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, bitmap.getWidth(), bitmap.getHeight());
			scaleAnimation.setDuration(1500);
			imageView.setImageBitmap(bitmap);
			imageView.startAnimation(scaleAnimation);
			break;
		case 2:
			MyAnimation1 myanimation1 = new MyAnimation1(1500);
			imageView.setImageBitmap(bitmap);
			imageView.startAnimation(myanimation1);
			break;
		case 3:
			MyAnimation2 myanimation2 = new MyAnimation2(1500);
			imageView.setImageBitmap(bitmap);
			imageView.startAnimation(myanimation2);
			break;
		case 4:
			AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
			alphaAnimation.setDuration(1500);
			imageView.setImageBitmap(bitmap);
			imageView.startAnimation(alphaAnimation);
			break;
		}

	}

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(480, 600)
				.threadPoolSize(4).threadPriority(3).denyCacheImageMultipleSizesInMemory().memoryCacheSize(4 * 1024 * 1024)
				.memoryCache(new WeakMemoryCache()).discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)).writeDebugLogs() // Remove
				.build();
		if (imageLoader == null)
			imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

}
