package cn.com.reformer.netty.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  Copyright 2017 the original author or authors hangzhou Reformer
 * @Description: ${todo}(这里用一句话描述这个类的作用)
 * @author zhangjin
 * @create 2017-05-08
**/
public class DateUtils {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYMMDD = "yyMMdd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD_HH_MM_SS_2 = "yyyy/MM/dd HH:mm:ss";
    private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    private static final String HH_MM_SS = "HH:mm:ss";
    private static final String YYMMDDHHMMSS_SSS = "yyMMddHHmmssSSS";
    public static final SimpleDateFormat FMT_YMD = new SimpleDateFormat(YYYY_MM_DD);
    public static final SimpleDateFormat FMT_YYYYMMDD = new SimpleDateFormat(YYYYMMDD);
    public static final SimpleDateFormat FMT_YYMMDD = new SimpleDateFormat(YYMMDD);
    public static final SimpleDateFormat FMT_YMDHMS = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    public static final SimpleDateFormat FMT_YMDHMS_1 = new SimpleDateFormat(YYYYMMDDHHMMSS);
    public static final SimpleDateFormat FMT_YMDHMS_2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_2);
    public static final SimpleDateFormat FMT_HMS = new SimpleDateFormat(HH_MM_SS);
    public static final SimpleDateFormat FMT_YYMMDDHHMMSS_SSS = new SimpleDateFormat(YYMMDDHHMMSS_SSS);
    //串口获取时间日期格式
    public static final SimpleDateFormat rxtx_gettime_format = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final int[] DAYS_OF_MONTH = {31, 28, 31, 30, 31, 30, 31,
            31, 30, 31, 30, 31};
    private static final String[] WEEKDAYS = {"星期日", "星期一", "星期二", "星期三", "星期四",
            "星期五", "星期六"};

    public static String formatDate(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return returnValue;
    }

    public static Date parseDate(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String formatDate(Date date, SimpleDateFormat sdf) {
        return sdf.format(date);
    }

    public static Date parseDate(String strDate, SimpleDateFormat sdf) {
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date addDateHour(Date date, Integer hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);

        return cal.getTime();
    }

    public static String addMinuteStr_ymdhms(String dt, int num) {
        Date date = null;
        try {
            date = FMT_YMDHMS.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, num);
        return FMT_YMDHMS.format(cal.getTime());
    }

    public static String addDateStr_ymdhms(String dt, int num) {
        Date date = addDates_ymdhms(dt, num);
        return FMT_YMD.format(date);
    }

    public static String addDateStr_ymd(String dt, int num) {
        Date date = addDates_ymd(dt, num);
        return FMT_YMD.format(date);
    }

    public static Date addDates_ymd(String dt, int num) {
        Date date = null;
        try {
            date = FMT_YMD.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return addDates(date, num);
    }

    public static Date addDates_ymdhms(String dt, int num) {
        Date date = null;
        try {
            date = FMT_YMDHMS.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return addDates(date, num);
    }

    public static Date addDates(Date date, int num) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, num);
        return cal.getTime();
    }

    public static String ymd_changeTo_ymdhms(String dt) {
        return dt + " 00:00:00";
    }

    public static String ymdhms_changeTo_ymd(String dt) {
        return dt.substring(0, YYYY_MM_DD.length());
    }

    // 2个日期相差多少天
    public static long getDayNumberBetweenTwoDays(Date startDate, Date endDate) {
        long t1 = startDate.getTime();
        long t2 = endDate.getTime();
        long count = (t2 - t1) / (24L * 60 * 60 * 1000);
        return Math.abs(count);
    }

    // 2个日期相差多少分钟
    public static long getMinuteNumBetweenTwoDays(String startDate, String endDate) {
        long count = 0L;
        try {
            Date st_time = FMT_YMDHMS.parse(startDate);
            Date ed_time = FMT_YMDHMS.parse(endDate);
            long t1 = ed_time.getTime();
            long t2 = st_time.getTime();
            count = (t2 - t1) / (60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Math.abs(count);
    }

    // 得到星期几
    public static String getWeekForDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return WEEKDAYS[w];
    }

    // 判断日期是否是周六周末
    public boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == 1 || dayOfWeek == 7;
    }

    // date所处周的星期一
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // date所处周的星期天
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    // 得到日期月份最大的天数
    public static int getMaxDayOfMonth(int year, int month) {
        if (month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0))
            return 29;
        return DAYS_OF_MONTH[month - 1];
    }

    // 判断2个日期是否在同一天
    public static boolean isSameDay(Date date, Date date2) {
        String str = formatDate(date, YYYY_MM_DD);
        String str2 = formatDate(date2, YYYY_MM_DD);
        return str.equals(str2);
    }

    // yyyy-MM-dd 0:00:00
    //days=0 今天,-1昨天,1明天下面的都一样
    public static Date getDateStart(Date date, int days) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);
        startCal.set(5, startCal.get(5) + days);
        startCal.set(11, 0);
        startCal.set(14, 0);
        startCal.set(13, 0);
        startCal.set(12, 0);
        return startCal.getTime();
    }

    // yyyy-MM-dd 23:59:59
    public static Date getDateEnd(Date date, int days) {
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(date);
        endCal.set(5, endCal.get(5) + days);
        endCal.set(11, 23);
        endCal.set(14, 59);
        endCal.set(13, 59);
        endCal.set(12, 59);
        return endCal.getTime();
    }

    // yyyy-MM-1 00:00:00
    public static Date getMonthStart(Date date, int n) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);
        startCal.set(5, 1);
        startCal.set(11, 0);
        startCal.set(14, 0);
        startCal.set(13, 0);
        startCal.set(12, 0);
        startCal.set(2, startCal.get(2) + n);
        return startCal.getTime();
    }

    // yyyy-MM-end 23:59:59
    public static Date getMonthEnd(Date date, int n) {
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(date);
        endCal.set(5, 1);
        endCal.set(11, 23);
        endCal.set(14, 59);
        endCal.set(13, 59);
        endCal.set(12, 59);
        endCal.set(2, endCal.get(2) + n + 1);
        endCal.set(5, endCal.get(5) - 1);
        return endCal.getTime();
    }

    // yyyy-1-1 00:00:00
    public static Date getYearStart(Date date, int n) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);
        startCal.set(2, 0);// JANUARY which is 0;
        startCal.set(5, 1);
        startCal.set(11, 0);
        startCal.set(12, 0);
        startCal.set(14, 0);
        startCal.set(13, 0);
        startCal.set(1, startCal.get(1) + n);
        return startCal.getTime();
    }

    // yyyy-12-31 23:59:59
    public static Date getYearEnd(Date date, int n) {
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(date);
        endCal.set(2, 12);
        endCal.set(5, 1);
        endCal.set(11, 23);
        endCal.set(14, 59);
        endCal.set(13, 59);
        endCal.set(12, 59);
        endCal.set(1, endCal.get(1) + n);
        endCal.set(5, endCal.get(5) - 1);
        return endCal.getTime();
    }

    // 日期加上n个月
    public static Date addMonths(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    // 2个日期相差多少天 不考虑时分秒
    public static long getDayDiffIgnoreHHMISS(Date startDate, Date endDate)
            throws ParseException {
        startDate = getDateStart(startDate, 0);
        endDate = getDateStart(endDate, 0);
        long t1 = startDate.getTime();
        long t2 = endDate.getTime();
        long count = (t2 - t1) / (24L * 60 * 60 * 1000);
        return Math.abs(count);
    }

    public static Boolean date1IsMoreDate2(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        if (t1 > t2) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    // 2个日期相差多少年
    public static int getYearDiff(Date minDate, Date maxDate) {
        if (minDate.after(maxDate)) {
            Date tmp = minDate;
            minDate = new Date(maxDate.getTime());
            maxDate = new Date(tmp.getTime());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DATE);

        calendar.setTime(maxDate);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DATE);
        int result = year2 - year1;
        if (month2 < month1) {
            result--;
        } else if (month2 == month1 && day2 < day1) {
            result--;
        }
        return result;
    }

    // 2个日期相差多少月
    public static int getMonthDiff(Date minDate, Date maxDate) {
        if (minDate.after(maxDate)) {
            Date tmp = minDate;
            minDate = new Date(maxDate.getTime());
            maxDate = new Date(tmp.getTime());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DATE);

        calendar.setTime(maxDate);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DATE);

        int months = 0;
        if (day2 >= day1) {
            months = month2 - month1;
        } else {
            months = month2 - month1 - 1;
        }
        return (year2 - year1) * 12 + months;
    }

    // 得到2个日期之间的月份,返回值List<字符串>
    public static List getMonthsBetween(Date minDate, Date maxDate) {
        ArrayList result = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (minDate.after(maxDate)) {
            Date tmp = minDate;
            minDate = new Date(maxDate.getTime());
            maxDate = new Date(tmp.getTime());
        }
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 最近一周时间段 今天对应的上周星期n-今天
     */
    public static Date[] getRecentWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date to = calendar.getTime();
        calendar.set(Calendar.WEEK_OF_MONTH,
                calendar.get(Calendar.WEEK_OF_MONTH) - 1);
        Date from = calendar.getTime();
        return new Date[]{from, to};
    }

    // 最近一个月 今天对应的上月n日-今天
    public static Date[] getRecentMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date to = calendar.getTime();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        Date from = calendar.getTime();
        return new Date[]{from, to};
    }

    // 中文显示日期与当前时间差
    public static String friendlyFormat(Date date) throws ParseException {
        if (date == null) {
            return "未知";
        }
        Date baseDate = new Date();
        if (baseDate.before(date)) {
            return "未知";
        }
        int year = getYearDiff(baseDate, date);
        int month = getMonthDiff(baseDate, date);
        if (year >= 1) {
            return year + "年前";
        } else if (month >= 1) {
            return month + "月前";
        }
        int day = (int) getDayNumberBetweenTwoDays(baseDate, date);
        if (day > 0) {
            if (day > 2) {
                return day + "天前";
            } else if (day == 2) {
                return "前天";
            } else if (day == 1) {
                return "昨天";
            }
        }
        if (!isSameDay(baseDate, date)) {
            return "昨天";
        }
        int hour = (int) ((baseDate.getTime() - date.getTime()) / (1 * 60 * 60 * 1000));
        if (hour > 6) {
            return "今天";
        } else if (hour > 0) {
            return hour + "小时前";
        }
        int minute = (int) ((baseDate.getTime() - date.getTime()) / (1 * 60 * 1000));
        if (minute < 2) {
            return "刚刚";
        } else if (minute < 30) {
            return minute + "分钟前";
        } else if (minute > 30) {
            return "半个小时以前";
        }
        return "未知";
    }

    /**
     * 20060718164830 -> 2006-07-18 16:48:30
     *
     * @param dateTimeBytes
     * @return
     */


    /**
     * 将长时间格式字符串转换为时间 yyyyMMdd
     *
     * @param strDate
     * @return
     */
    public static Date stringToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);

    }

    /**
     * 将长时间格式字符串转换为时间 HHmm
     *
     * @param strDate
     * @return
     */
    public static Time stringToTime(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        ParsePosition pos = new ParsePosition(0);
        return new Time(formatter.parse(strDate, pos).getTime());

    }

    public static String getWeekFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 1)
            w = 7;
        return "0" + w;
    }
}
