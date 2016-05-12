package com.cookbeans.boredapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cookbeans.boredapp.ui.func.OnDaNiuMeizhiTouchListener;

/**
 * Created by dongsj on 16/5/13.
 */
public class DaNiuListTableRecyclerViewAdapter
        extends AnimRecyclerViewAdapter<DaNiuListTableRecyclerViewAdapter.DaNiuItemViewHolder> {
    private static final String TAG = DaNiuListTableRecyclerViewAdapter.class.getSimpleName();

    private OnDaNiuMeizhiTouchListener mOnDaNiuMeizhiTouchListener;


    public void setOnDaNiuMeizhiTouchListener(OnDaNiuMeizhiTouchListener listener) {
        this.mOnDaNiuMeizhiTouchListener = listener;
    }


    class DaNiuItemViewHolder extends RecyclerView.ViewHolder {

        public DaNiuItemViewHolder(View itemView) {
            super(itemView);

        }
    }
}
