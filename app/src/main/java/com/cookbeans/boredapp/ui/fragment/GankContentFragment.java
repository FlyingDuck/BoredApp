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
        testMeizhi.desc = "05月03日:搞笑动画短片《水手与死神》，现在的死神啊，一言不合就开始卖萌！";
        testMeizhi.createdAt = new Date();
        testMeizhi.publishedAt = new Date();
        testMeizhi.updatedAt = new Date();
        Meizhi testMeizhi_2 = new Meizhi();
        testMeizhi_2.url = "http://ww1.sinaimg.cn/large/7a8aed7bgw1f3j8jt6qn8j20vr15owvk.jpg";
        testMeizhi_2.type = "福利";
        testMeizhi_2.who = "张涵宇";
        testMeizhi_2.used = true;
        testMeizhi_2.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testMeizhi_2.createdAt = new Date();
        testMeizhi_2.publishedAt = new Date();
        testMeizhi_2.updatedAt = new Date();
        Meizhi testMeizhi_3 = new Meizhi();
        testMeizhi_3.url = "http://ww3.sinaimg.cn/large/7a8aed7bjw1f39v1uljz8j20c50hst9q.jpg";
        testMeizhi_3.type = "福利";
        testMeizhi_3.who = "张涵宇";
        testMeizhi_3.used = true;
        testMeizhi_3.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testMeizhi_3.createdAt = new Date();
        testMeizhi_3.publishedAt = new Date();
        testMeizhi_3.updatedAt = new Date();
        Meizhi testMeizhi_4 = new Meizhi();
        testMeizhi_4.url = "http://ww2.sinaimg.cn/large/610dc034gw1f35cxyferej20dw0i2789.jpg";
        testMeizhi_4.type = "福利";
        testMeizhi_4.who = "张涵宇";
        testMeizhi_4.used = true;
        testMeizhi_4.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testMeizhi_4.createdAt = new Date();
        testMeizhi_4.publishedAt = new Date();
        testMeizhi_4.updatedAt = new Date();


        mMeizhiList.add(testMeizhi);
        mMeizhiList.add(testMeizhi_2);
        mMeizhiList.add(testMeizhi_3);
        mMeizhiList.add(testMeizhi_4);
        mMeizhiList.add(testMeizhi_2);
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
