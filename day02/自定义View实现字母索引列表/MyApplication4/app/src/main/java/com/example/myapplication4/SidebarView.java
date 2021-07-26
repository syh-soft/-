package com.example.myapplication4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SidebarView extends LinearLayout implements View.OnTouchListener {
    private Context n_context;
    private LinearLayout Layout;
    private TextView Title;

    public SidebarView(Context context) {
        this(context, null, 0);
    }

    public SidebarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SidebarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        n_context = context;
        LayoutInflater.from(n_context).inflate(R.layout.wg_sidebar, this);
        Layout = (LinearLayout) findViewById(R.id.wg_sidebar_layout);
        Title = (TextView) findViewById(R.id.wg_sidebar_title);

        Layout.setOnTouchListener(this);
        List<String> listData = new ArrayList<>();
        listData.add("A");
        listData.add("B");
        listData.add("C");
        listData.add("D");
        listData.add("E");
        listData.add("F");
        listData.add("G");
        listData.add("H");
        listData.add("I");
        listData.add("J");
        listData.add("K");
        listData.add("L");
        listData.add("M");
        listData.add("N");
        listData.add("O");
        listData.add("P");
        listData.add("Q");
        listData.add("R");
        listData.add("S");
        listData.add("T");
        listData.add("U");
        listData.add("V");
        listData.add("W");
        listData.add("X");
        listData.add("Y");
        listData.add("Z");
        setSideDae(listData);
    }


    public void setSideDae(List<String> listData) {
        Layout.removeAllViews();
        for (String str : listData) {
            SidebarItemView tv = new SidebarItemView(n_context);
            tv.setTag(str);
            tv.setText(str);
            Layout.addView(tv);
        }
    }

    private boolean isDown = false;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            isDown = true;
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            Title.setVisibility(View.GONE);
            isDown = false;
        }
        transformValue((LinearLayout) view, motionEvent);
        return true;
    }


    private void transformValue(LinearLayout layout, MotionEvent motionEvent) {
        SidebarItemView textView = (SidebarItemView) layout.getChildAt(0);
        float fistViewTop = textView.getTop();
        float itemHeight = textView.getMeasuredHeight();
        float currentY = motionEvent.getY();

        if (motionEvent.getY() > 0 && motionEvent.getY() < layout.getMeasuredHeight()) {

            if (currentY > fistViewTop && currentY < (fistViewTop + itemHeight * layout.getChildCount())) {
                float rang = currentY - fistViewTop;
                int index = (int) (rang / itemHeight);
                Log.e("kawa", "rang:" + rang + "_index:" + index + "_itemHeight:" + itemHeight);
                Title.setText((String) (layout.getChildAt(index).getTag()));
                if (listener!=null){
                    listener.onSelectItem(index,(String) (layout.getChildAt(index).getTag()));
                }
                if (isDown) {
                    Title.setVisibility(View.VISIBLE);
                    setAnimation(index, layout);
                }
            }
        }
    }


    private void setAnimation(int index, LinearLayout layout) {
        if (index > 1 && index < layout.getChildCount() - 2) {
            SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index - 1);
            SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index);
            SidebarItemView tv3 = (SidebarItemView) layout.getChildAt(index + 1);
            tv1.setItemAnimation2();
            tv2.setItemAnimation();
            tv3.setItemAnimation2();
        } else {

            if (index == 0) {
                SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index);
                SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index + 1);
                tv1.setItemAnimation();
                tv2.setItemAnimation2();
            }
            else {
                SidebarItemView tv1 = (SidebarItemView) layout.getChildAt(index);
                SidebarItemView tv2 = (SidebarItemView) layout.getChildAt(index - 1);
                tv1.setItemAnimation();
                tv2.setItemAnimation2();
            }
        }
    }

    private OnSidebarViewListener listener;

    public void setOnSidebarViewListener(OnSidebarViewListener l) {
        listener = l;
    }

    public interface OnSidebarViewListener {
        void onSelectItem(int index, String value);
    }
}