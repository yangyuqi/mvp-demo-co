package com.wxjz.tenms_pad.viewPagerTransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 浮出动画
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float v) {
        int pageWidth = view.getWidth();
        if (v < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
        } else if (v <= 0) {// [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (v <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - v);
            // Counteract the default slide transition
            // 这一步很重要，设置了这个才有浮出的效果，否则效果是从左右进出，而不是上下
            view.setTranslationX(pageWidth * -v);
            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(v));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else {// (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
