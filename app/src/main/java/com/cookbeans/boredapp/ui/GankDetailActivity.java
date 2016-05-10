package com.cookbeans.boredapp.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cookbeans.boredapp.R;
import com.cookbeans.boredapp.utils.DateTimeUtils;

import java.util.Date;

public class GankDetailActivity extends AppCompatActivity {

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
    }
}
