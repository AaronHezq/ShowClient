package com.aaron.showclient.view;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
 
public class MyAnimation1 extends Animation {
    private int halfWidth;
    private int halfHeight;
    private int duration;
    public MyAnimation1(int duration){
        this.duration = duration;
    }
    
	@Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        //进行缩放，此时的interpolatedTime表示缩放的比例，interpolatedTime的时间，开始时间，结束时间
        matrix.preScale(interpolatedTime, interpolatedTime); 
        matrix.preRotate(interpolatedTime * 360); //进行旋转
        //改变动画的起始位置，把扩散点和起始点移到中间
        matrix.preTranslate(-halfWidth, -halfHeight); 
        matrix.postTranslate(halfWidth, halfHeight);
    }
    @Override
    public void initialize(int width, int height, int parentWidth,
            int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.setDuration(duration); //设置动画播放的时间
        this.setFillAfter(true); //设置为true，动画结束的时候保持动画效果
        this.halfHeight = height / 2; //动画对象的中点坐标
        this.halfWidth = width / 2;
        this.setInterpolator(new LinearInterpolator()); 
    }
 
}