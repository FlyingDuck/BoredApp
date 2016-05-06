package com.cookbeans.boredapp.utils;

import com.cookbeans.boredapp.data.gank.entity.Gank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by dongsj on 16/5/6.
 */
public final class TestDatas {

    private static List<Gank> gankList = new ArrayList<>();

    static {
        Gank testGank = new Gank();
        testGank.url = "http://ww1.sinaimg.cn/large/7a8aed7bgw1f3damign7mj211c0l0dj2.jpg";
        testGank.type = "福利";
        testGank.who = "张涵宇";
        testGank.used = true;
        testGank.desc = "05月03日:搞笑动画短片《水手与死神》，现在的死神啊，一言不合就开始卖萌！";
        testGank.createdAt = new Date();
        testGank.publishedAt = new Date();
        testGank.updatedAt = new Date();
        Gank testGank_2 = new Gank();
        testGank_2.url = "http://ww1.sinaimg.cn/large/7a8aed7bgw1f3j8jt6qn8j20vr15owvk.jpg";
        testGank_2.type = "福利";
        testGank_2.who = "张涵宇";
        testGank_2.used = true;
        testGank_2.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testGank_2.createdAt = new Date();
        testGank_2.publishedAt = new Date();
        testGank_2.updatedAt = new Date();
        Gank testGank_3 = new Gank();
        testGank_3.url = "http://ww3.sinaimg.cn/large/7a8aed7bjw1f39v1uljz8j20c50hst9q.jpg";
        testGank_3.type = "福利";
        testGank_3.who = "张涵宇";
        testGank_3.used = true;
        testGank_3.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testGank_3.createdAt = new Date();
        testGank_3.publishedAt = new Date();
        testGank_3.updatedAt = new Date();
        Gank testGank_4 = new Gank();
        testGank_4.url = "http://ww2.sinaimg.cn/large/610dc034gw1f35cxyferej20dw0i2789.jpg";
        testGank_4.type = "福利";
        testGank_4.who = "张涵宇";
        testGank_4.used = true;
        testGank_4.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testGank_4.createdAt = new Date();
        testGank_4.publishedAt = new Date();
        testGank_4.updatedAt = new Date();
        Gank testGank_5 = new Gank();
        testGank_5.url = "http://ww4.sinaimg.cn/large/610dc034jw1f3litmfts1j20qo0hsac7.jpg";
        testGank_5.type = "福利";
        testGank_5.who = "张涵宇";
        testGank_5.used = true;
        testGank_5.desc = "05月04日:泰国网红的心酸日常》：凄美的浪漫，虚无与真实.";
        testGank_5.createdAt = new Date();
        testGank_5.publishedAt = new Date();
        testGank_5.updatedAt = new Date();

        gankList.add(testGank);
        gankList.add(testGank_2);
        gankList.add(testGank_3);
        gankList.add(testGank_4);
        gankList.add(testGank_5);
    }

    public static List<Gank> getAny() {
        return getAnyLength(-1);
    }

    public static List<Gank> getAnyLength(int length) {
        Random random = new Random(System.currentTimeMillis());
        int size = gankList.size();
        if (0 > length) {
            length = random.nextInt(size);
        }
        List<Gank> result = new ArrayList<>(size);
        while (length-- > 0) {
            int index = random.nextInt(size);
            Gank gank = gankList.get(index);
            result.add(gank);
        }
        return result;
    }

    public static List<Gank> getAll() {
        return gankList;
    }
}
