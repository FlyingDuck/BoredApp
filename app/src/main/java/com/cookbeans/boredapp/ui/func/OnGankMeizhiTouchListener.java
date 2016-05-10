package com.cookbeans.boredapp.ui.func;

import android.view.View;

import com.cookbeans.boredapp.data.gank.entity.Gank;

/**
 * Created by dongsj on 16/5/10.
 */
public interface OnGankMeizhiTouchListener {
    void onTouch(View view, View meizhiView, View titleView, View card, Gank meizhi);
}
