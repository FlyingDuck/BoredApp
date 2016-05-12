package com.cookbeans.boredapp.service;

import com.cookbeans.boredapp.data.daniu.net.DaNiuResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by dongsj on 16/5/12.
 */
public interface DaNiuApi {
    int LOAD_LIMIT = 20;

    @GET("/data/all/{page}/"+LOAD_LIMIT)
    Observable<DaNiuResult> getAllDaNiuData(@Path("page") int page);

    @GET("/data/请您欣赏/{page}/"+LOAD_LIMIT)
    Observable<DaNiuResult> getEnjoyData(@Path("page") int page);

}
