package com.zjy.dandelion.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 提供日期时间辅助方法
 *
 *
 *
 *
 * Created by zhuangjy on 2015 下午4:31:29.
 *
 */
public class DateTimeUtil {

    public static final String TIME_END = " 23:59:59";
    public static final String TIME_START = " 00:00:00";

    /**
     * 获取相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenDay(final Date date1, final Date date2) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();

        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        final long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 根据日期计算相差天数(忽略时分秒)
     *
     * @param early
     *            日期
     * @param late
     *            日期
     * @return 相差天数
     */
    public static int getBetweenDayIgnoreTime(final Date early, final Date late) {
        final Calendar cal_1 = Calendar.getInstance();
        cal_1.setTime(early);

        final Calendar cal_2 = Calendar.getInstance();
        cal_2.setTime(late);

        cal_1.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal_1.set(java.util.Calendar.MINUTE, 0);
        cal_1.set(java.util.Calendar.SECOND, 0);
        cal_2.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal_2.set(java.util.Calendar.MINUTE, 0);
        cal_2.set(java.util.Calendar.SECOND, 0);

        final long time_1 = cal_1.getTimeInMillis() / 1000;
        final long time_2 = cal_2.getTimeInMillis() / 1000;

        return (int) ((time_2 - time_1) / 3600 / 24);
    }

    /**
     * 获取相差秒 date1>date2
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenSeconds(final Date date1, final Date date2) {

        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final long time1 = cal.getTimeInMillis();

        cal.setTime(date2);
        final long time2 = cal.getTimeInMillis();
        final long between_days = (time1 - time2) / 1000;

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String getCurrentDate() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    /**
     * 获取yyyy-MM-dd格式日期
     *
     * @param date
     *            日期
     * @return yyyy-MM-dd格式日期
     */
    public static String getDate(final Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String getDate(final Date date, final String format) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取日期时间的当天最大时间的日期时间
     *
     * @param date
     *            日期时间
     * @return 当天最大时间的日期时间
     */
    public static Date getDateAndMaxTime(final Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = dateTimeFormat.parse(dateFormat.format(date)
                    + " 23:59:59");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 获取前后日期
     *
     * @param date
     * @param day
     * @return
     */
    public static String getDateByDay(final Date date, final int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        final Date createDate = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
    }

    /**
     * 获取日期时间的日期
     *
     * @param date
     *            日期时间
     * @return 日期
     */
    public static Date getDateDay(final Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateFormat.format(date));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期时间的日期
     *
     * @param date
     *            日期,格式yyyy-MM-dd
     * @return 日期
     */
    public static Date getDateDay(final String date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前日期的日期
     *
     * @param date
     * @return 返回的日期
     */
    public static int getDateOnly(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取日期时间的
     *
     * @param date
     *            日期,格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getDateTime(final String time) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(time);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日期的最早时刻
     *
     * @param date
     * @return
     */
    public static Date getDateWithFirstTime(final Date date) {
        return getDateWithFirstTime(getDate(date));
    }

    /**
     * 获取日期的最早时刻
     *
     * @param date
     * @return
     */
    public static Date getDateWithFirstTime(final String date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date + " 00:00:00");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateWithLastTime(final Date date) {
        return getDateWithLastTime(getDate(date));
    }

    /**
     * 获取日期的最后时刻
     *
     * @param date
     * @return
     */
    public static Date getDateWithLastTime(final String date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date + " 23:59:59");
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取前天日期
     *
     * @return
     */
    public static String getDayBeforeYesterday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        final Date createDate = calendar.getTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
    }

    /**
     * 返回当前日期的昨日日期
     *
     * @return 日期的String类型
     */
    public static String getLastDay() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        final Date createDate = calendar.getTime();

        return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
    }

    public static String getLastDay(final String day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateDay(day));
        calendar.add(Calendar.DATE, -1);
        final Date createDate = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
    }

    /**
     * 获取上一天的月份
     *
     * @return
     */
    public static String getLastMonth() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        final Date createDate = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM").format(createDate);
    }

    /**
     * 返回当前日期的月份
     *
     * @param date
     * @return 返回的月份
     */
    public static int getMonth(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static String getNextDay(final String day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateDay(day));
        calendar.add(Calendar.DATE, 1);
        final Date createDate = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(createDate);
    }

    /**
     * 返回当前日期的年份
     *
     * @param date
     * @return 返回的年份
     */
    public static int getYear(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static void main(final String[] args) {
        System.out.println(new Date().after(DateTimeUtil
                .getDateTime(DateTimeUtil.getCurrentDate() + " 15:00:00")));
        final Date date = new Date();
        System.out.println("date : " + date);
        System.out.println("getYear : " + DateTimeUtil.getYear(date));
        System.out.println("getMonth : " + DateTimeUtil.getMonth(date));
        System.out.println("getDate : " + DateTimeUtil.getDate(date));
    }

}
