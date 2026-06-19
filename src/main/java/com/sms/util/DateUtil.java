package com.sms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date == null) return "";
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String getCurrentSemester() {
        Date now = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(yearFormat.format(now));
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        int month = Integer.parseInt(monthFormat.format(now));

        if (month >= 2 && month <= 7) {
            return (year - 1) + "-" + year + "-2";
        } else {
            return year + "-" + (year + 1) + "-1";
        }
    }
}