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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.data.gank.entity.Meizhi;
import com.cookbeans.boredapp.ui.adapter.GankMeizhiRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 *
 * 干货集中营数据Fragement
 * http://gank.io/
 */
public class GankContentFragment extends Fragment {
    public static final String TAG = GankContentFragment.class.getSimpleName();

    public static final String KEY_POSITION = "position";


    private List<Meizhi> mMeizhiList = new ArrayList<>();


    private List<String> testData1 = new ArrayList<String>(){{
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
    }};

    private RecyclerView mRecyclerView;
    private GankMeizhiRecyclerViewAdapter mGankMeizhiRecyclerViewAdapter;
//    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    
    private boolean mIsFirstTimeTouchBottom = true;
    private static final int PRELOAD_SIZE = 6;

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
        // TODO: 16/5/2 测试数据
        Meizhi testMeizhi = new Meizhi();
        testMeizhi.url = "http://ww1.sinaimg.cn/large/7a8aed7bgw1f3damign7mj211c0l0dj2.jpg";
        testMeizhi.type = "福利";
        testMeizhi.who = "张涵宇";
        testMeizhi.used = true;
        testMeizhi.desc = "测试数据,不要大惊小怪.";
        testMeizhi.createdAt = new Date();
        testMeizhi.publishedAt = new Date();
        testMeizhi.updatedAt = new Date();
        mMeizhiList.add(testMeizhi);
        mMeizhiList.add(testMeizhi);
        mMeizhiList.add(testMeizhi);
        mMeizhiList.add(testMeizhi);
        mMeizhiList.add(testMeizhi);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager_gank_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            int position = args.getInt(KEY_POSITION);
            // TODO: 16/4/29 meizhi数据
//            mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_meizhi);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(layoutManager);

            mGankMeizhiRecyclerViewAdapter = new GankMeizhiRecyclerViewAdapter(getActivity(), this, mMeizhiList);
            mRecyclerView.setAdapter(mGankMeizhiRecyclerViewAdapter);
        }
    }

}
