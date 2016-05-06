package com.cookbeans.boredapp.data.gank;

import com.cookbeans.boredapp.data.gank.entity.Gank;

import java.util.List;

/**
 * Created by dongsj on 16/5/6.
 */
public class GankResult {
    private List<Gank> gankList;

    public GankResult(List<Gank> gankList) {
        this.gankList = gankList;
    }

    public List<Gank> getGankList() {
        return gankList;
    }

    public void setGankList(List<Gank> gankList) {
        this.gankList = gankList;
    }
}
