package com.cookbeans.boredapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cookbeans.boredapp.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ImageView imageView = (ImageView) findViewById(R.id.iv_test);
        // http://goo.gl/gEgYUd
        // http://ww1.sinaimg.cn/large/7a8aed7bgw1f3damign7mj211c0l0dj2.jpg
        Glide.with(this)
                .load("http://goo.gl/gEgYUd")
                .error(R.drawable.girl)
                .placeholder(R.drawable.ic_menu_camera)
                .fitCenter()
                .into(imageView);
    }
}
