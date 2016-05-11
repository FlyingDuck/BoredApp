package com.cookbeans.boredapp.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookbeans.boredapp.R;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsj on 16/5/6.
 */
public abstract class BaseLoadingFragment extends Fragment {
    private ProgressActivity mProgressActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressActivity = (ProgressActivity) inflater.inflate(R.layout.fragment_base_loading_layout, container, false);
        mProgressActivity.addView(onCreateContentView(inflater, mProgressActivity, savedInstanceState));
        return mProgressActivity;
    }

    abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void showLoading() {
        mProgressActivity.showLoading();
    }

    protected void showContent(){
        mProgressActivity.showContent();
    }

    protected void showEmpty(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, List<Integer> skipIds) {
        if(null == skipIds)skipIds = new ArrayList<>();
        mProgressActivity.showEmpty(emptyImageDrawable, emptyTextTitle, emptyTextContent,skipIds);
    }

    protected void showError(Drawable emptyImageDrawable, String emptyTextTitle, String emptyTextContent, String errorButtonText, View.OnClickListener onClickListener) {
        mProgressActivity.showError(emptyImageDrawable, emptyTextTitle, emptyTextContent, errorButtonText, onClickListener);
    }
}
