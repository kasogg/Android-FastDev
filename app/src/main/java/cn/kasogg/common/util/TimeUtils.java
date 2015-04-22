package cn.kasogg.common.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATETIME_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATETIME_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static Date parseDate(String dateString, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date parseDate(String dateString) {
        return parseDate(dateString, DEFAULT_DATETIME_FORMAT);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getApproximateTime(long time) {
        long millSecond = 1000;
        long minute = millSecond * 60;
        long hour = minute * 60;
        long day = hour * 24;
        String timeString = "";
        long cha = System.currentTimeMillis() - time;
        if (cha == 0) {
            timeString = "刚刚";
        } else if (cha / millSecond < 60) {
            timeString = (cha / millSecond) + "秒前";
        } else if (cha / minute < 60) {
            timeString = (cha / minute) + "分钟前";
        } else if (cha / hour < 24) {
            timeString = (cha / hour) + "小时前";
        } else if (cha / day < 5) {
            timeString = (cha / day) + "天前";
        } else {
            timeString = new SimpleDateFormat("yyyy.MM.dd").format(time);
        }
        return timeString;
    }
}
