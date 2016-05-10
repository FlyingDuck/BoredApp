package com.cookbeans.boredapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.ui.fragment.SampleSlidingTabsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        SampleSlidingTabsFragment.Callback{
    private static final String TAG = MainActivity.class.getSimpleName();

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;

//    private List<View> toolbarViewPagerAds;
    private int[] toolbarViewPagerAds = new int[]{R.drawable.error_image, R.drawable.meizhi_default};


    private boolean mIsExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        /*[Start] toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitle(R.string.toolbar_name);
        setSupportActionBar(toolbar);
        /*[End] toolbar */


        /*[Start] appbar */
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        // toolbar layout
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        // toolbarLayout.setTitleEnabled(false);
        toolbarLayout.setTitle(getString(R.string.toolbar_name));
        toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        // toolbarLayout.setExpandedTitleGravity(Gravity.);

        // 实现appbar滑动到一定程度自动收缩功能
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxHeight = appBarLayout.getTotalScrollRange();
                float scrolledPercent = Math.abs(verticalOffset)*1.0F/maxHeight;
            }

        });
        /*[End] appbar */


        /* [Start] toolbar viewpager */
        // toolbar 广告  TODO: 16/5/2  暂时用一个简单的viewpager代替
        ViewPager toolbarViewPager = (ViewPager) findViewById(R.id.toolbar_viewpager);
        PagerAdapter toolbarViewPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return toolbarViewPagerAds.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeViewAt(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Ads "+position;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView adImg = new ImageView(MainActivity.this);
                adImg.setImageResource(toolbarViewPagerAds[position]);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
                adImg.setLayoutParams(layoutParams);
                container.addView(adImg);
                return adImg;
            }
        };
        toolbarViewPager.setAdapter(toolbarViewPagerAdapter);
        /* [End] toolbar viewpager */

        /* [Start] 浮动按钮 */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email Address : dongshujin.beans@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /* [End] 浮动按钮 */

        /* [Start] 抽屉菜单 */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /* [End] 抽屉菜单 */


        /* [Start] 住内容界面 */
        // main content layout
        if (null == savedInstanceState) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SampleSlidingTabsFragment sampleSlidingTabsFragment = new SampleSlidingTabsFragment();
            transaction.replace(R.id.content_fragment, sampleSlidingTabsFragment);
            transaction.commit();
        }
        /* [End] 住内容界面 */

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, TestActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void changeToolbarLayoutBGColor(int appBarBackGroundColor) {
        Log.d(TAG, "change toolbar layout ground color");
        toolbarLayout.setBackgroundColor(appBarBackGroundColor);
    }

    @Override
    public void changeToolbarLayoutScrimColor(int color) {
        Log.d(TAG, "change toolbar layout scrim ground color");
        toolbarLayout.setContentScrimColor(color);
    }

    @Override
    public void changeToolbarTitle(String title) {
        Log.d(TAG, "change toolbar title ground color");
        // TODO
    }
}
