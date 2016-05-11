package com.cookbeans.boredapp.data.gank.net;

import com.cookbeans.boredapp.data.gank.entity.Gank;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dongsj on 16/5/8.
 */
public class GankDayResult {
    public Result results;
    public List<String> category;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> videoList;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> meizhiList;
        @SerializedName("拓展资源")
        public List<Gank> expandList;
        @SerializedName("瞎推荐")
        public List<Gank> recommendList;
        @SerializedName("App")
        public List<Gank> appList;
    }

}
