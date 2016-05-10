package com.cookbeans.boredapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cookbeans.boredapp.R;

public class GankDetailActivity extends AppCompatActivity {

    public static final String EXTRA_GANK_DATE = "gank_publish_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
    }
}
