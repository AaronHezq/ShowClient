package com.aaron.showclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zcw.togglebutton.ToggleButton;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;

public class MainActivity extends FragmentActivity {

	private SlidingMenu menu;

	private ToggleButton toggleButton;

	private int menuIndex = 0;
	
	private Fragment1 fragment1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragment1 = new Fragment1();
		addFragmentToStack();
		initMenu();
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

	private void addFragmentToStack() {
		try {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			switch (menuIndex) {
			case 0:// 首页
//				if (fragment1 != null && fragment1.isAdded()) {
//					ft.hide(fragment1);
//				}
				if (fragment1.isAdded()) {
					ft.show(fragment1);
				} else {
					ft.add(R.id.activity_home, fragment1);
				}
				break;
			}
			ft.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
