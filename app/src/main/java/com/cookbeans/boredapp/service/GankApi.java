package com.cookbeans.boredapp.service;

import com.cookbeans.boredapp.data.gank.net.GankDayResult;
import com.cookbeans.boredapp.data.gank.net.GankResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by dongsj on 16/5/6.
 */
public interface GankApi {
    /**每次加载条目*/
    int LOAD_LIMIT = 20;
    /**加载起始页面*/
    int LOAD_START = 1;

    @GET("/data/福利/" + LOAD_LIMIT + "/{page}")
    Observable<GankResult> getMeizhiData(@Path("page") int page);

    @GET("/data/休息视频/" + LOAD_LIMIT + "/{page}")
    Observable<GankResult> getVideoData(@Path("page") int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankDayResult> getGankDayData(@Path("year") int year,
                                             @Path("month") int month,
                                             @Path("day") int day);



}
