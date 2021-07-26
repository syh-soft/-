package com.example.myapplication4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SidebarItemView extends LinearLayout {
    private Context n_context;
    private LinearLayout Layout;
    private TextView Title;
    private int screenWidth;

    public SidebarItemView(Context context) {
        this(context, null, 0);
    }

    public SidebarItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SidebarItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        n_context = context;
        LayoutInflater.from(n_context).inflate(R.layout.wg_sidebar_item, this);
        Layout = (LinearLayout) findViewById(R.id.wg_sidebar_item_root);
        Title = (TextView) findViewById(R.id.wg_sidebar_item_title);
        screenWidth = getScreenWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / 4, LayoutParams.WRAP_CONTENT);
        Layout.setLayoutParams(lp);
    }


    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) n_context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    public void setText(String text) {
        Title.setText(text);
    }


    public void setItemAnimation() {
        setAnimation(-(screenWidth / 12), -(screenWidth / 12));
    }


    public void setItemAnimation2() {
        setAnimation(-(screenWidth / 22), -(screenWidth / 22));
    }


    private void setAnimation(float fromXDelta, float toXDelta) {
        AnimationSet animationSet = new AnimationSet(true);
        final TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, 0, 0);
        animation.setDuration(50);
        animation.setFillAfter(false);
        animationSet.addAnimation(animation);
        Title.startAnimation(animation);
        animation.startNow();
    }
}