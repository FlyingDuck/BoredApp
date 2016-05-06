/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cookbeans.boredapp.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.gank.GankResult;
import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.cookbeans.boredapp.service.GankCloudApi;
import com.cookbeans.boredapp.ui.adapter.GankRecyclerViewAdapter;
import com.cookbeans.boredapp.utils.TestDatas;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 *
 * 干货集中营数据Fragement
 * http://gank.io/
 */
public class GankContentFragment extends BaseLoadingFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = GankContentFragment.class.getSimpleName();

    public static final String KEY_POSITION = "position";


    private List<Gank> mGankList;

    private RecyclerView mRecyclerView;
    private GankRecyclerViewAdapter mGankRecyclerViewAdapter;
    private SwipeRefreshLayout  mSwipeRefreshLayout;

    private int hasLoadPage = 0;
    private int mLastVisibleItem;
    private boolean isLoadMore = false;
    private boolean isALlLoad = false;
    
        /**
         * @return a new instance of {@link GankContentFragment}, adding the parameters into a bundle and
         * setting them as arguments.
         */
    public static GankContentFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POSITION, position);

        GankContentFragment fragment = new GankContentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankRecyclerViewAdapter = new GankRecyclerViewAdapter(getActivity());
        mGankList = new ArrayList<>();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
    // return inflater.inflate(R.layout.view_pager_gank_item, container, false);
//    }

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager_gank_item, null);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_meizhi);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mGankRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorSecondary, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Bundle args = getArguments();

        if (args != null) {
            int position = args.getInt(KEY_POSITION);
            Log.d(TAG, "onViewCreate postion : " + position);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
        reloadData();
    }

    @Override
    public void onRefresh() {
        reloadData();
    }

    private RecyclerView.OnScrollListener getOnBottomListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mGankRecyclerViewAdapter.getItemCount()) {
                    loadMore();
                }
            }
        };
    }

    private void loadMore(){
        Log.d(TAG, "loadMore");
        if(isLoadMore) {
            return;
        }
        if(isALlLoad){
            Snackbar.make(mRecyclerView,"全部加载完毕",Snackbar.LENGTH_SHORT)
                    .setAction("知道了",null)
                    .show();
            return;
        }
        isLoadMore = true;
        loadData(hasLoadPage + 1);
    }

    private void reloadData(){
        mSwipeRefreshLayout.setRefreshing(true);
        mGankList.clear();
        isALlLoad = false;
        hasLoadPage = 0;
        loadData(1);
    }

    private void loadData(int startPage){
        Log.d(TAG, "loadData startPage : " + startPage);
        mSwipeRefreshLayout.setRefreshing(true);
        // TODO: 16/5/6 网络请求数据
        // 暂时 使用假数据
        List<Gank> ganks = null;
        if (4 == startPage){
            ganks = TestDatas.getAnyLength(GankCloudApi.LOAD_LIMIT-1);
        } else {
            ganks = TestDatas.getAnyLength(GankCloudApi.LOAD_LIMIT);
        }

        final GankResult gankResult = new GankResult(ganks);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                disposeResults(gankResult);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 1000);

    }

    private void disposeResults(final GankResult gankResult){
        if(mGankList.isEmpty() && gankResult.getGankList().isEmpty()){
            showNoDataView();
            return;
        }
        showContent();
        if(gankResult.getGankList().size() == GankCloudApi.LOAD_LIMIT){
            hasLoadPage++;
        }else {
            isALlLoad = true;
        }
        isLoadMore = false;
        mGankList.addAll(gankResult.getGankList());
        mGankRecyclerViewAdapter.updateItems(mGankList, hasLoadPage == 1);
    }

    private void showNoDataView(){
        Drawable emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart)
                .colorRes(android.R.color.white);
        List<Integer> skipIds = new ArrayList<>();
        showEmpty(emptyDrawable, "数据列表为空", "没有拿到数据哎，请等一下再来玩干货吧", skipIds);
    }

}
