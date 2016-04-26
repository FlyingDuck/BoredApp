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

package com.cookbeans.boredapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class SimpleContentFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";



    private List<String> testData1 = new ArrayList<String>(){{
        add(    "CEO:kate\n" +
                "2B 架构：董望舒\n" +
                "三流的产品:#\n" +
                "四线商务：#\n" +
                "五线运营：思思神\n" +
                "\n" +
                "惊叹号网络科技有限没钱公司，于2016年3月3日成立在宇宙中心出租房内，这是一帮真的在苟且，但任然有梦想和远方的理想主义者，他们是善良的，真诚的，勇敢的。\n" +
                "希望我们能成吧（称霸）。");
    }};

    private List<String> testData2 = new ArrayList<String>(){{
        add(    "1.APP开发（看心情和人品接活，价钱：真的朋友的活不要钱，假的朋友请自觉付费）。\n" +
                "2.燃气报警器\n" +
                "3.空气售卖机");
    }};

    private List<String> testData3 = new ArrayList<String>(){{
        add(    "挖人的自觉绕行。\n" +
                "不挖人的联系18618361932");
    }};


        /**
         * @return a new instance of {@link SimpleContentFragment}, adding the parameters into a bundle and
         * setting them as arguments.
         */
    public static SimpleContentFragment newInstance(int position,
                                                    CharSequence title,
                                                    int indicatorColor,
                                                    int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putInt("position", position);
        bundle.putInt(KEY_INDICATOR_COLOR, indicatorColor);
        bundle.putInt(KEY_DIVIDER_COLOR, dividerColor);

        SimpleContentFragment fragment = new SimpleContentFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager_sample_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
//            TextView title = (TextView) view.findViewById(R.id.item_title);
//            title.setText("Title: " + args.getCharSequence(KEY_TITLE));
//
//            int indicatorColor = args.getInt(KEY_INDICATOR_COLOR);
//            TextView indicatorColorView = (TextView) view.findViewById(R.id.item_indicator_color);
//            indicatorColorView.setText("Indicator: #" + Integer.toHexString(indicatorColor));
//            indicatorColorView.setTextColor(indicatorColor);
//
//            int dividerColor = args.getInt(KEY_DIVIDER_COLOR);
//            TextView dividerColorView = (TextView) view.findViewById(R.id.item_divider_color);
//            dividerColorView.setText("Divider: #" + Integer.toHexString(dividerColor));
//            dividerColorView.setTextColor(dividerColor);

            // Test
            int position = args.getInt("position");
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
            List<String> testData = new ArrayList<>();
            if (1 == position) {
                testData = testData1;
            } else if (2 == position) {
                testData = testData2;
            } else if (3 == position) {
                testData = testData3;
            } else {

            }
            recyclerView.setAdapter(new RecyclerViewAdapter(testData));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
