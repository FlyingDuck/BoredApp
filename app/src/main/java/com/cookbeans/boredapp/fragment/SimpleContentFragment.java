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

/**
 * Simple Fragment used to display some meaningful content for each page in the sample's
 * {@link android.support.v4.view.ViewPager}.
 */
public class SimpleContentFragment extends Fragment {

    private static final String KEY_TITLE = "title";
    private static final String KEY_INDICATOR_COLOR = "indicator_color";
    private static final String KEY_DIVIDER_COLOR = "divider_color";

    /**
     * @return a new instance of {@link SimpleContentFragment}, adding the parameters into a bundle and
     * setting them as arguments.
     */
    public static SimpleContentFragment newInstance(CharSequence title, int indicatorColor,
            int dividerColor) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
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
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
            recyclerView.setAdapter(new RecyclerViewAdapter(new String[]{
                    "俞正声说，2015年是全面深化改革的关键之年、全面推进依法治国的开局之年，也是人民政协事业开拓奋进、创新发展的重要一年。一年来，以习近平同志为总书记的中共中央高度重视政协工作，进一步加强对人民政协的政治、思想和组织领导，中央政治局常委会会议多次研究政协工作，中共中央出台加强社会主义协商民主建设的意见和加强人民政协协商民主建设的实施意见召开中央统战工作会议，颁布实施《中国共产党统一战线工作条例(试行)》",
                    "The RecyclerView widget is a more advanced and flexible version of ListView. This widget is a container for displaying large data sets that can be scrolled very efficiently by maintaining a limited number of views. Use the RecyclerView widget when you have data collections whose elements change at runtime based on user action or network events.",
                    "Animations for adding and removing items are enabled by default in RecyclerView. To customize these animations, extend the RecyclerView.ItemAnimator class and use the RecyclerView.setItemAnimator() method.",
                    "4",
                    "5","6","7","8","9","10","11","12","13","14","15","16","17","18"}));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}
