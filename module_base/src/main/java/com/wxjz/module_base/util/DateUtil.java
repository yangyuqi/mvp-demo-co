package com.wxjz.module_base.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;


import com.wxjz.module_base.constant.BasisConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间工具类
 */
public class DateUtil {

    /**
     * 时间格式化模式：yyyy-MM-dd HH:mm:ss
     */
    private static final String DATE_FORMAT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式化模式：yyyy-MM-dd
     */
    private static final String DATE_FORMAT_SIMPLE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式化模式：yyyy.MM.dd
     */
    private static final String DATE_FORMAT_SIMPLE_PATTERN_POINT = "yyyy.MM.dd";
    /**
     * 格式化时间：yyyy-MM-dd HH:mm:ss
     */
    private final static SimpleDateFormat DATE_FORMAT_FULL = new SimpleDateFormat
            (DATE_FORMAT_FULL_PATTERN, Locale.CHINA);
    /**
     * 格式化时间：yyyy-MM-dd
     */
    private final static SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat
            (DATE_FORMAT_SIMPLE_PATTERN, Locale.CHINA);
    /**
     * 格式化时间：yyyy.MM.dd
     */
    private final static SimpleDateFormat DATE_FORMAT_SIMPLE_POINT = new SimpleDateFormat
            (DATE_FORMAT_SIMPLE_PATTERN_POINT, Locale.CHINA);
    /**
     * 分钟前
     */
    private static final String BEFORE_MINUTE = "分钟前";
    /**
     * 小时前
     */
    private static final String BEFORE_HOUR = "小时前";
    /**
     * 昨天
     */
    private static final String YESTERDAY = "昨天";
    /**
     * 前天
     */
    private static final String BEFORE_DAY = "前天";
    /**
     * 天前
     */
    private static final String DAY_BEFORE = "天前";
    /**
     * 未知
     */
    private static final String DAY_UNKNOWN = "未知";
    private static final String MONTH_DAY_TIME = "MM月dd日 HH:mm";
    private static final String HOUR_MINUTE = "HH:mm";
    private static final String YESTERDAY_HOUR_MINUTE = "昨天HH:mm";
    private static final String Y_YESTERDAY_HOUR_MINUTE = "前天HH:mm";
    private static final SimpleDateFormat SDF_MD = new SimpleDateFormat(MONTH_DAY_TIME, Locale
            .CHINA);
    private static final SimpleDateFormat SDF_HM = new SimpleDateFormat(HOUR_MINUTE, Locale.CHINA);
    private static final SimpleDateFormat SDF_YHM = new SimpleDateFormat(YESTERDAY_HOUR_MINUTE,
            Locale.CHINA);
    private static final SimpleDateFormat SDF_Y_YHM = new SimpleDateFormat
            (Y_YESTERDAY_HOUR_MINUTE, Locale.CHINA);

    /**
     * 将时间转换为yyyy.MM.dd格式
     *
     * @param time 待转换时间
     * @return 字符串
     */
    public static String getDataSplitByPoint(Date time) {
        try {
            return DATE_FORMAT_SIMPLE_POINT.format(time);
        } catch (Exception e) {
            return BasisConstants.NULL_STRING;
        }
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        @SuppressLint("WrongConstant") int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 将字符串转为日期类型
     * <br/>
     * 转换格式为：yyyy-MM-dd
     *
     * @param _formaterTime 待转换的时间字符串
     * @return 转换的时间
     */
    public static Date toDate(String _formaterTime) {
        try {
            return DATE_FORMAT_SIMPLE.parse(_formaterTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间 <br/>
     * 将时间格式化转换为类似多少分钟/小时/几天前的方式
     *
     * @param _formaterTime 待转换的时间
     * @return 转换后友好的时间格式
     */
    public static String toFriendlyTime(String _formaterTime) {
        Date time;
        if (isInEasternEightZones()) {
            time = DateUtil.toDate(_formaterTime);
        } else {
            time = transformTime(DateUtil.toDate(_formaterTime),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        return toFriendlyTime(time);
    }

    public static String formateNYRSFM(long timestap) {
        Date date = new Date(timestap);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 以友好的方式显示时间 <br/>
     * 将时间格式化转换为类似多少分钟/小时/几天前的方式
     * <p>
     * 待转换的时间
     *
     * @return 转换后友好的时间格式
     */
    public static String toFriendlyTime(Date time) {

        if (time == null) {
            return DAY_UNKNOWN;
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = DATE_FORMAT_FULL
                .format(cal.getTime());
        String paramDate = DATE_FORMAT_FULL.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + BEFORE_MINUTE;
            } else {
                ftime = hour + BEFORE_HOUR;
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {

                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + BEFORE_MINUTE;
            } else {
                ftime = hour + BEFORE_HOUR;
            }
        } else if (days == 1) {
            ftime = YESTERDAY;
        } else if (days == 2) {
            ftime = BEFORE_DAY;
        } else if (days > 2 && days <= 10) {
            ftime = days + DAY_BEFORE;
        } else if (days > 10) {
            ftime = DATE_FORMAT_FULL.format(time);
        }
        return ftime;
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        defaultVaule = TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     */
    public static Date transformTime(Date date, TimeZone oldZone,
                                     TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;

    }


    public static String toSimpleTime(long time) {
        return toSimpleTime(new Date(time));
    }

    public static String toSimpleTime(Date date) {
        if (isSameDay(date.getTime())) {
            return SDF_HM.format(date);
        } else if (isYesterday(date.getTime())) {
            return SDF_YHM.format(date);
        } else if (isTheDayBeforeYesterday(date.getTime())) {
            return SDF_Y_YHM.format(date);
        }
        return SDF_MD.format(date);
    }

    /**
     * 优化格式化时间2
     */
    public static String toFriendlyTime2(Date date) {
        String text;
        long dateTime = date.getTime();
        if (isSameDay(dateTime)) {
            Calendar calendar = GregorianCalendar.getInstance();
            if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
                return "刚刚";
            } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
                return String.format(Locale.CHINA, "%d分钟之前", Math.abs(dateTime - calendar
                        .getTimeInMillis()) / 60000);
            } else {
                calendar.setTime(date);
                @SuppressLint("WrongConstant") int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                if (hourOfDay > 17) {
                    text = "晚上 hh:mm";
                } else if (hourOfDay >= 0 && hourOfDay <= 6) {
                    text = "凌晨 hh:mm";
                } else if (hourOfDay > 11 && hourOfDay <= 17) {
                    text = "下午 hh:mm";
                } else {
                    text = "上午 hh:mm";
                }
            }
        } else if (isYesterday(dateTime)) {
            text = "昨天 HH:mm";
        } else if (isSameYear(dateTime)) {
            text = "M月d日 HH:mm";
        } else {
            text = "yyyy-M-d HH:mm";
        }

        // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
        return new SimpleDateFormat(text, Locale.CHINA).format(date);
    }

    /**
     * 优化格式化时间
     */
    public static String toFriendlyTimeHHMM(Date date) {
        try {
            String text = null;
            long dateTime = date.getTime();
            if (isSameDay(dateTime)) {
                Calendar calendar = GregorianCalendar.getInstance();
                if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
                    return "刚刚";
                } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
                    return String.format(Locale.CHINA, "%d分钟之前", Math.abs(dateTime - calendar
                            .getTimeInMillis()) / 60000);
                } else {
                    calendar.setTime(date);
                    @SuppressLint("WrongConstant") int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                    if (hourOfDay > 17) {
                        text = "晚上 hh:mm";
                    } else if (hourOfDay >= 0 && hourOfDay <= 6) {
                        text = "凌晨 hh:mm";
                    } else if (hourOfDay > 11 && hourOfDay <= 17) {
                        text = "下午 hh:mm";
                    } else {
                        text = "上午 hh:mm";
                    }
                }
            }
            // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
            return new SimpleDateFormat(text, Locale.CHINA).format(date);
        } catch (Exception e) {
            return DATE_FORMAT_FULL.format(date);
        }
    }

    /**
     * 优化格式化时间
     */
    public static String toFriendlyTimeYMD(Date date) {
        try {
            String text;
            long dateTime = date.getTime();
            if (isYesterday(dateTime)) {
                text = "昨天";
            } else if (isSameYear(dateTime)) {
                text = "M月d日";
            } else {
                text = "yyyy-M-d";
            }

            // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
            return new SimpleDateFormat(text, Locale.CHINA).format(date);
        } catch (Exception e) {
            return DATE_FORMAT_FULL.format(date);
        }
    }

    private static boolean inOneMinute(long time1, long time2) {
        return Math.abs(time1 - time2) < 60000;
    }

    private static boolean inOneHour(long time1, long time2) {
        return Math.abs(time1 - time2) < 3600000;
    }

    public static boolean isSameDay(long time) {
        long startTime = floorDay().getTimeInMillis();
        long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
        return time > startTime && time < endTime;
    }

    @SuppressLint("WrongConstant")
    private static boolean isYesterday(long time) {
        Calendar startCal;
        startCal = floorDay();
        startCal.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = startCal.getTimeInMillis();

        Calendar endCal;
        endCal = ceilDay(Calendar.getInstance());
        endCal.add(Calendar.DAY_OF_MONTH, -1);
        long endTime = endCal.getTimeInMillis();
        return time > startTime && time < endTime;
    }

    @SuppressLint("WrongConstant")
    private static boolean isTheDayBeforeYesterday(long time) {
        Calendar startCal;
        startCal = floorDay();
        startCal.add(Calendar.DAY_OF_MONTH, -2);
        long startTime = startCal.getTimeInMillis();

        Calendar endCal;
        endCal = ceilDay(Calendar.getInstance());
        endCal.add(Calendar.DAY_OF_MONTH, -2);
        long endTime = endCal.getTimeInMillis();
        return time > startTime && time < endTime;
    }

    @SuppressLint("WrongConstant")
    private static boolean isSameYear(long time) {
        Calendar startCal = floorDay();
        startCal.set(Calendar.MONTH, Calendar.JANUARY);
        startCal.set(Calendar.DAY_OF_MONTH, 1);
        long start = startCal.getTimeInMillis();
        startCal.add(Calendar.YEAR, 1);
        long end = startCal.getTimeInMillis();
        return time >= start && time <= end;
    }

    /**
     * 今日凌晨
     */
    @SuppressLint("WrongConstant")
    private static Calendar floorDay() {
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return startCal;
    }

    @SuppressLint("WrongConstant")
    private static Calendar ceilDay(Calendar endCal) {
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        return endCal;
    }

    public static String formatAppTime(Date date) {
        try {
            String text;
            long timeStamp = date.getTime();
            if (isSameDay(timeStamp)) {
                text = "今天 HH:mm";
            } else if (isYesterday(timeStamp)) {
                text = "昨天 HH:mm";
            } else if (isTheDayBeforeYesterday(timeStamp)) {
                text = "前天 HH:mm";
            } else if (isThisWeek(timeStamp)) {
                text = "EEEE HH:mm";
            } else if (isSameYear(timeStamp)) {
                text = "MM月dd日 HH:mm";
            } else {
                text = "yyyy年MM月dd日 HH:mm";
            }
            return new SimpleDateFormat(text, Locale.CHINA).format(date);
        } catch (Exception e) {
            return DATE_FORMAT_FULL.format(date);
        }
    }

    public static String formatKf5Time(long timeStamp) {
        try {
            String text;
            if (isSameDay(timeStamp)) {
                text = "HH:mm";
            } else if (isYesterday(timeStamp)) {
                text = "昨天 HH:mm";
            } else if (isTheDayBeforeYesterday(timeStamp)) {
                text = "前天 HH:mm";
            } else if (isSameYear(timeStamp)) {
                text = "MM月dd日 HH:mm";
            } else {
                text = "yyyy年MM月dd日 HH:mm";
            }
            return new SimpleDateFormat(text, Locale.CHINA).format(timeStamp);
        } catch (Exception e) {
            return DATE_FORMAT_FULL.format(timeStamp);
        }
    }

    //是否是本周
    public static boolean isThisWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        return paramWeek == currentWeek;
    }

    /**
     * 时间格式化
     */
    public static String formattedTime(long second) {
        String hs, ms, ss, formatTime;

        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }

        formatTime = hs + ":" + ms + ":" + ss;
        return formatTime;
    }

    /**
     * 时间格式化
     */
    public static String formattedMSTime(int second) {
        int ms = second / 60;
        int ss = second % 60;
        return ms + "分" + ss + "秒";
    }

    /**
     * 得到昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(calendar.getTime());
        return yesterday;
    }

    /**
     * 得到今天的日期
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(new Date());
    }

    public static String getFormatTime(long sec) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(sec);
        return sdf.format(date);
    }

    public static int getTimeDiffer(long differ) {
        return (int) differ / (24 * 3600 * 1000);
    }

    public static String getDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        return month + "月" + day + "日";
    }

    public static String getWeekDay() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String weekDay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(weekDay)) {
            weekDay = "日";
        } else if ("2".equals(weekDay)) {
            weekDay = "一";
        } else if ("3".equals(weekDay)) {
            weekDay = "二";
        } else if ("4".equals(weekDay)) {
            weekDay = "三";
        } else if ("5".equals(weekDay)) {
            weekDay = "四";
        } else if ("6".equals(weekDay)) {
            weekDay = "五";
        } else if ("7".equals(weekDay)) {
            weekDay = "六";
        }
        return "周" + weekDay;
    }

    public static Date getTermEnd(String startDate, int dayNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDate));
            for (int i = 0; i < dayNum; i++) {
                c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
            }
        } catch (ParseException p) {

        }
        return c1.getTime();
    }

    public static String formatTimeStamp(long timestamp) {
        //时间格式,HH是24小时制，hh是AM PM12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String date_string = sdf.format(new Date(timestamp));
        return date_string;
    }

    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};


    public static String getConstellation(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        int month = 0;
        int day = 0;

        String[] constell = date.split("-");
        try {
            if (constell != null && constell.length == 3) {
                month = Integer.valueOf(constell[1]);
                day = Integer.valueOf(constell[2]);
            }
        } catch (Exception e) {
            return "";
        }

        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

}
