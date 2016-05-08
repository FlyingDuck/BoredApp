package com.cookbeans.boredapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dongsj on 16/5/8.
 */
public final class DateTimeUtils {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";


    public static String dateToDefaultStr(Date date) {
        return dateToStr(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String dateToStr(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}
