package com.zkzy.portal.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author admin
 */
public class DateHelper extends DateUtils {

    public static final int DEFAULT = 0;
    public static final int YM = 1;
    public static final int YMR_SLASH = 11;
    public static final int NO_SLASH = 2;
    public static final int YM_NO_SLASH = 3;
    public static final int DATE_TIME = 4;
    public static final int DATE_TIME_NO_SLASH = 5;
    public static final int DATE_HM = 6;
    public static final int TIME = 7;
    public static final int HM = 8;
    public static final int LONG_TIME = 9;
    public static final int SHORT_TIME = 10;
    public static final int DATE_TIME_LINE = 12;

    /**
     * 日期格式
     */
    private static final String[] PARSE_PATTERNS = {
        "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
        "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
        "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * 日期格式
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期时间格式
     */
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     *
     * @return the date
     */
    public static String getDate() {
        return getDate(DEFAULT_DATE_FORMAT);
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern the pattern
     * @return the date
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, DEFAULT_DATE_FORMAT);
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     *
     * @param date the date
     * @return the string
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     *
     * @return the time
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     *
     * @return the date time
     */
    public static String getDateTime() {
        return formatDate(new Date(), DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     *
     * @return the year
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     *
     * @return the month
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     *
     * @return the day
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     *
     * @return the week
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     *
     * @param str the str
     * @return the date
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), PARSE_PATTERNS);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date 对比日期
     * @return long long
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date 对比日期
     * @return long long
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date 对比日期
     * @return long long
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis 毫秒数
     * @return 天, 时:分:秒.毫秒
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = timeMillis / (60 * 60 * 1000) - day * 24;
        long min = timeMillis / (60 * 1000) - day * 24 * 60 - hour * 60;
        long s = timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
        long sss = timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000;
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before 开始日期
     * @param after  结束日期
     * @return 天数 distance of two date
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (double) (1000 * 60 * 60 * 24);
    }

    /**
     * 获取东八区当前时间
     *
     * @return Date est 8 date
     */
    public static Date getEst8Date() {
        TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        dateFormat.setTimeZone(tz);
        return parseDate(dateFormat.format(date));
    }

    public static String dateToStr(Date date, String pattern)
    {
        if ((date == null) || (date.equals("")))
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }


    public static String dateToStr(Date date) {
        return dateToStr(date, "yyyy/MM/dd");
    }


    public static String dateToStr(Date date, int type) {
        switch (type)
        {
            case 0:
                return dateToStr(date);
            case 1:
                return dateToStr(date, "yyyy/MM");
            case 2:
                return dateToStr(date, "yyyyMMdd");
            case 11:
                return dateToStr(date, "yyyy-MM-dd");
            case 3:
                return dateToStr(date, "yyyyMM");
            case 4:
                return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
            case 5:
                return dateToStr(date, "yyyyMMddHHmmss");
            case 6:
                return dateToStr(date, "yyyy/MM/dd HH:mm");
            case 7:
                return dateToStr(date, "HH:mm:ss");
            case 8:
                return dateToStr(date, "HH:mm");
            case 9:
                return dateToStr(date, "HHmmss");
            case 10:
                return dateToStr(date, "HHmm");
            case 12:
                return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        }
        throw new IllegalArgumentException("未定义的type : " + type+"》》》》 请使用0-12");
    }

}
