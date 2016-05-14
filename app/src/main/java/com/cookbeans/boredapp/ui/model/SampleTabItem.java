package com.cookbeans.boredapp.ui.model;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.cookbeans.boredapp.ui.fragment.DaNiuListTableFragment;
import com.cookbeans.boredapp.ui.fragment.GankListTableFragment;
import com.cookbeans.boredapp.ui.fragment.MeizhiListTableFragment;
import com.cookbeans.boredapp.ui.view.SlidingTabLayout;

/**
 * Created by dongsj on 16/4/28.
 * This class represents a tab to be displayed by {@link ViewPager} and it's associated
 * {@link SlidingTabLayout}.
 *
 * 标签内容
 */
public class SampleTabItem {
    public static final int POS_GANK             =   1;  // gank
    public static final int POS_DANIU            =   2;  // 大牛俱乐部
    public static final int POS_UNDIFINED_TOO    =   3;  // 就是妹纸

    private int             mPostion;       // 标签位置
    private CharSequence    mTitle;         // 标签标题
    private int             mIndicatorColor;    // 标签底部导航条颜色
    private int             mDividerColor;      // 标签分隔符颜色 note: 这里暂未使用该属性
    private int[]           mBGColors;      // 背景色 标签背景色/工具条背景色/scrim背景色

    public SampleTabItem(int position,
                         CharSequence title, int indicatorColor, int dividerColor,
                         int tabBackGroundColor,
                         int toolbarLayoutBGColor,
                         int toolbarLayoutScrimColor) {
        mPostion = position;
        mTitle = title;
        mIndicatorColor = indicatorColor;
        mDividerColor = dividerColor;
        mBGColors = new int[]{tabBackGroundColor, toolbarLayoutBGColor, toolbarLayoutScrimColor};
    }

    /**
     * @return A new {@link Fragment} to be displayed by a {@link ViewPager}
     */
    public Fragment createFragment() {
        Fragment fragment = null;
        switch (mPostion) {
            case POS_GANK:
                fragment = GankListTableFragment.newInstance(POS_GANK);
                break;
            case POS_DANIU:
                fragment = DaNiuListTableFragment.newInstance(POS_DANIU);
                break;
            case POS_UNDIFINED_TOO:
                fragment = MeizhiListTableFragment.newInstance(POS_UNDIFINED_TOO);
                break;
            default:
                throw new UnsupportedOperationException("暂未定义其他Fragment");
        }
        return fragment;
    }

    /**
     * @return the title which represents this tab. In this sample this is used directly by
     * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
     */
    public CharSequence getTitle() {
        return mTitle;
    }

    /**
     * @return the color to be used for indicator on the {@link SlidingTabLayout}
     */
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    /**
     * @return the color to be used for right divider on the {@link SlidingTabLayout}
     */
    public int getDividerColor() {
        return mDividerColor;
    }

    /**
     * @return 标签/工具条等背景色
     */
    public int[] getBGColors() {
        return mBGColors;
    }

}
