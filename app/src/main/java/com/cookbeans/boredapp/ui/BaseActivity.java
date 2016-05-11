package com.cookbeans.boredapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cookbeans.boredapp.service.ApiFactory;
import com.cookbeans.boredapp.service.GankApi;

public class BaseActivity extends AppCompatActivity {

    public static final GankApi gankApi = ApiFactory.getGankApiSingleton();


}
