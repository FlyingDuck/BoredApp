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

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookbeans.boredapp.ui.MainActivity;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.ui.model.SampleTabItem;
import com.cookbeans.boredapp.ui.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic sample which shows how to use {@link SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SampleSlidingTabsFragment extends Fragment {
    private static final String TAG = SampleSlidingTabsFragment.class.getSimpleName();

    public interface Callback {
        void changeToolbarLayoutBGColor(int color);
        void changeToolbarLayoutScrimColor(int color);
    }

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * List of {@link SampleTabItem} which represent this sample's tabs.
     */
    private List<SampleTabItem> mTabs = new ArrayList<SampleTabItem>();

    private Callback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof MainActivity) {
            Log.d(TAG, "Simple Sliding Tabs Fragment is started by MainActivity");
            MainActivity activity = (MainActivity) context;
            callback = activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BEGIN_INCLUDE (populate_tabs)
        /**
         * Populate our tab list with tabs. Each item contains a title, indicator color and divider
         * color, which are used by {@link SlidingTabLayout}.
         */
        mTabs.add(new SampleTabItem(
                SampleTabItem.POS_GANK,
                "干货集中营", // Title
                getResources().getColor(R.color.colorIndicator), // Indicator color
//                Color.WHITE,
                Color.GRAY, // Divider color
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        ));

        mTabs.add(new SampleTabItem(
                SampleTabItem.POS_DANIU,
                "大牛俱乐部", // Title
                getResources().getColor(R.color.colorIndicator), // Indicator color
//                Color.WHITE,
                Color.GRAY, // Divider color
                getResources().getColor(R.color.deepPurplePrimaryDark),
                getResources().getColor(R.color.deepPurplePrimary),
                getResources().getColor(R.color.deepPurplePrimaryDark)
        ));

        mTabs.add(new SampleTabItem(
                SampleTabItem.POS_UNDIFINED_TOO,
                "就是妹子", // Title
                getResources().getColor(R.color.colorIndicator), // Indicator color
//                Color.WHITE,
                Color.GRAY, // Divider color
                getResources().getColor(R.color.pinkPrimaryDark),
                getResources().getColor(R.color.pinkPrimary),
                getResources().getColor(R.color.pinkPrimaryDark)
        ));
        // END_INCLUDE (populate_tabs)
    }

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_tabs, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of
     * {@link SampleFragmentPagerAdapter}. The {@link SlidingTabLayout} is then given the
     * {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setSlidingTabCallBack(callback);
        mSlidingTabLayout.setViewPager(mViewPager);

        // BEGIN_INCLUDE (tab_colorizer)
        // Set a TabColorizer to customize the indicator and divider colors. Here we just retrieve
        // the tab at the position, and return it's set color
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }

        });
        // END_INCLUDE (tab_colorizer)
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link FragmentPagerAdapter} used to display pages in this sample. The individual pages
     * are instances of {@link GankListTableFragment} which just display three lines of text. Each page is
     * created by the relevant {@link SampleTabItem} for the requested position.
     * <p>
     * The important section of this class is the {@link #getPageTitle(int)} method which controls
     * what is displayed in the {@link SlidingTabLayout}.
     */
    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the {@link android.support.v4.app.Fragment} to be displayed at {@code position}.
         * <p>
         * Here we return the value returned from {@link SampleTabItem#createFragment()}.
         */
        @Override
        public Fragment getItem(int i) {
            return mTabs.get(i).createFragment();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we return the value returned from {@link SampleTabItem#getTitle()}.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        public int[] getToolbarAndTabBGColor(int position) {
            return mTabs.get(position).getBGColors();
        }

    }

}