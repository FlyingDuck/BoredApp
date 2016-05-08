package com.cookbeans.boredapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by dongsj on 16/5/8.
 */
public class ApiRetrofit {

    private final GankApi gankApi;

    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on

    ApiRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(12, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://gank.io/api")
                .setConverter(new GsonConverter(gson));
        RestAdapter gankRestAdapter = builder.build();
//        builder.setEndpoint("https://leancloud.cn:443/1.1/classes");
//        RestAdapter drakeetRestAdapter = builder.build();
        gankApi = gankRestAdapter.create(GankApi.class);
    }


    public GankApi getGankApiService() {
        return gankApi;
    }
}
