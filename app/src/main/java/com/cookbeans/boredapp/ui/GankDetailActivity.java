package com.cookbeans.boredapp.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.ui.fragment.GankDetailTableFragment;
import com.cookbeans.boredapp.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class GankDetailActivity extends BaseActivity {

    public static final String EXTRA_GANK_DATE = "gank_publish_date";
    public static final String EXTRA_GANK_MEIZHI_URL = "gank_meizhi_url";

    public Date mGankPublishDate;
    public String mMeizhiUrl;

    private CollapsingToolbarLayout toolbarLayout;

    private ImageView gankMeizhiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);

        mGankPublishDate = (Date) getIntent().getSerializableExtra(EXTRA_GANK_DATE);
        mMeizhiUrl = getIntent().getStringExtra(EXTRA_GANK_MEIZHI_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(getString(R.string.gank_detail_toolbar_name) + " " + DateTimeUtils.dateToStr(mGankPublishDate, DateTimeUtils.DEFAULT_DATE_FORMAT_2));

        gankMeizhiView = (ImageView) findViewById(R.id.iv_gank_detail_meizhi);

        Glide.with(this)
                .load(mMeizhiUrl)
                .placeholder(R.drawable.meizhi_default)
                .error(R.drawable.error_image)
//                .fitCenter()
                .centerCrop()
                .into(gankMeizhiView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GankDetailActivity.this.onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email: dongshujin.beans@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mGankPublishDate);

        if (null == savedInstanceState) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            GankDetailTableFragment gankDetailTableFragment =
                    GankDetailTableFragment.newInstance(
                            calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
            transaction.replace(R.id.fragment_gank_detail_conten, gankDetailTableFragment);
            transaction.commit();
        }

    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank_detail, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_share:
                Snackbar.make(toolbarLayout, "分享 嘿嘿嘿......", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                ShareUtils.shareImage(this, mMeizhiUrl, "来自干货集中营的妹纸["+DateTimeUtils.dateToDefaultStr(mGankPublishDate)+"]");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
