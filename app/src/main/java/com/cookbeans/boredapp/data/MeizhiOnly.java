package com.cookbeans.boredapp.data;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by dongshujin on 16/5/14.
 */
@Table("meizhi_only")
public class MeizhiOnly extends Soul{

    @Column("description")
    public String desc;
    @Column("url")
    public String url;

    @Column("imageWidth")
    public int width = 50;
    @Column("imageHeight")
    public int height = 50;
    @Column("publishedAt")
    public Date publishedAt;
}
