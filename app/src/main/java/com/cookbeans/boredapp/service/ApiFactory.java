package com.cookbeans.boredapp.service;

/**
 * Created by dongsj on 16/5/11.
 */
public class ApiFactory {
    protected static final Object monitor = new Object();
    private static GankApi sGankApi = null;
//    private static DaniuApi sDaniutSingleton = null;


    public static GankApi getGankApiSingleton() {
        if (null == sGankApi) {
            synchronized (monitor) {
                if (null == sGankApi) {
                    sGankApi = new ApiRetrofit().getGankApiService();
                }
            }
        }
        return sGankApi;
    }

}
