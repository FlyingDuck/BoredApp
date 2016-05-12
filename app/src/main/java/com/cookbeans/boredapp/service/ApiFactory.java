package com.cookbeans.boredapp.service;

/**
 * Created by dongsj on 16/5/11.
 */
public class ApiFactory {
    protected static final Object monitor = new Object();
    private static GankApi gankApiInstance = null;
    private static DaNiuApi daNiuApiInstance = null;


    public static GankApi getGankApiSingleton() {
        if (null == gankApiInstance) {
            synchronized (monitor) {
                if (null == gankApiInstance) {
                    gankApiInstance = new ApiRetrofit().getGankApiService();
                }
            }
        }
        return gankApiInstance;
    }

    public static DaNiuApi getDaNiuApiInstance() {
        if (null == daNiuApiInstance) {
            synchronized (monitor) {
                if (null == daNiuApiInstance) {
                    daNiuApiInstance = new ApiRetrofit().getDaNiuApiService();
                }
            }
        }
        return daNiuApiInstance;
    }

}
