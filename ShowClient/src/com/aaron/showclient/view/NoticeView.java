package com.aaron.showclient.view;


import java.text.AttributedCharacterIterator.Attribute;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("DrawAllocation")
public class NoticeView extends TextView {

	public String content = "niw都很温暖法规玩玩撒哇乖娃娃naj";
	public String bgColor = null;
	public String fontColor = null;
	public int size;
//	public Typeface tf1;// 标题字体
	public Handler handler;
	public static boolean ismove;
	public float startpoint;
	private boolean islive = true;
	private TextPaint paint_title = new TextPaint();
	public int width;
	public int height;
	public float speed;//移动速度
	private FontMetrics fontMetrics;
	private float fontHeight;
	private boolean isupdate;

	public NoticeView(Context context) {
		super(context);
	}
	
	public NoticeView(Context context,AttributeSet attribute) {
		super(context,attribute);
	}
	
	
	
	public void setData() {
		this.width = 500;
		this.height = 60;
		this.startpoint = width;
		paint_title.setAntiAlias(true);// 锯齿
		size = height / 10 * 7;
		paint_title.setTextSize(size);
//		paint_title.setTypeface(tf1);
		this.fontMetrics = paint_title.getFontMetrics();
		this.fontHeight = fontMetrics.bottom - fontMetrics.top;// 计算文字高度
		this.speed = fontHeight/50;
		scollText();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(content == null) return;
		if(fontColor != null) {
			try {
				paint_title.setColor(Color.parseColor(fontColor));
			} catch (Exception e) {
				System.out.println("setNoteColor Exception");
				paint_title.setColor(Color.WHITE);
				e.printStackTrace();
			}
		}else paint_title.setColor(Color.WHITE);
		if(bgColor != null) {
			setBackgroundColor(Color.parseColor(bgColor));
		}
		//setBackgroundResource(R.drawable.bg);
		float titlelen = paint_title.measureText(content);
		if (titlelen >= width) {
			ismove = true;
		} else ismove = false;
		
		float ypoint = getHeight() - (getHeight() - fontHeight) / 2
				- fontMetrics.bottom;
		if (!ismove) {
			startpoint = getWidth() / 2;
			paint_title.setTextAlign(Align.CENTER);
			canvas.drawText(content, startpoint, ypoint, paint_title);
		} else {
			canvas.drawText(content, startpoint, ypoint, paint_title);
		}

	}
	
	//用于单独更新通知界面，检测通知是否有更新
	public boolean checkUpdate(boolean isupdate) {
		return isupdate;
	}

	
	public void scollText() {
		new Thread() {
			public void run() {
				while (islive) {
					float titlelen = paint_title.measureText(content);
					if (ismove) {
						if(content == null) continue;
						if (startpoint < -titlelen) {
							startpoint = width;
						} else {
							startpoint -= speed;
							NoticeView.this.postInvalidate();
						}
					}
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	
}
