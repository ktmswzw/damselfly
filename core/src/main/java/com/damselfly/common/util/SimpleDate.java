
package com.damselfly.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class SimpleDate
{
    public static void main(String[] args)
    {
        System.out.println(SimpleDate.format(SimpleDate.getDayStart(new Date(), 1), DATE_PATTEN_TM_1));
    }

    public static String DATE_PATTEN = "yyyy-MM-dd";

    public static String DATE_PATTEN_MM = "yyyy-MM-dd HH:mm";

    public static String DATE_PATTEN_TM = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_PATTEN_TM_1 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String DATE_PATTEN_ZH = "yyyy年MM月dd日";

    public static String DATETM_PATTEN_ZH = "yyyy年MM月dd日 HH点mm分ss秒";

    public static String TM_PATTEN_ZH = "HH点mm分ss秒";

    public static String formatDateTime(Date date)
    {
        String s = DateFormatUtils.format(date, DATE_PATTEN_TM);
        return s;
    }

    public static String format(Date date)
    {
        String s = DateFormatUtils.format(date, DATE_PATTEN);
        return s;
    }

    public static String formatZH(Date date)
    {
        String s = DateFormatUtils.format(date, DATE_PATTEN_ZH);
        return s;
    }

    public static String format(Date date, String patten)
    {
        String s = DateFormatUtils.format(date, patten);
        return s;
    }

    public static String format(long time, String patten)
    {
        String s = DateFormatUtils.format(new Date(time), patten);
        return s;
    }

    public static Date strToDate(String str)
    {
        try
        {
            Date date = DateUtils.parseDate(str, new String[] { DATE_PATTEN, DATE_PATTEN_MM, DATE_PATTEN_ZH, DATE_PATTEN_TM,
                    DATE_PATTEN_TM_1 });
            return date;
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date getDateTime()
    {
            return new Timestamp(System.currentTimeMillis());
    }

    public static Date strToDate(String str, String pattern)
    {
        try
        {
            Date date = DateUtils.parseDate(str, new String[] { pattern });
            return date;
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date getDayStart(Date date)
    {
        return getDayStart(date, 0);
    }

    public static Date getDayStart(Date date, int daysLater)
    {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        tempCal.set(Calendar.MILLISECOND, 0);
        tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
        return tempCal.getTime();
    }

    public static Date getDayEnd(Date date)
    {
        return getDayEnd(date, 0);
    }

    public static Date getDayEnd(Date date, int daysLater)
    {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.set(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), tempCal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        tempCal.set(Calendar.MILLISECOND, 0);
        tempCal.add(Calendar.DAY_OF_MONTH, daysLater);
        return tempCal.getTime();
    }

    public static Date intToDateDaily(int dailyInt)
    {
        String daily = String.valueOf(dailyInt);
        try
        {
            return DateUtils.parseDate(daily, new String[] { "yyyyMMdd" });
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static Date intToDateMonth(int dailyInt)
    {
        String moth = String.valueOf(dailyInt);
        try
        {
            return DateUtils.parseDate(moth, new String[] { "yyyyMM" });
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e);
        }
    }

    public static String fmtIntDaily(int dailyInt)
    {
        String daily = String.valueOf(dailyInt);
        return fmtDaily(daily);
    }

    public static String fmtDaily(String daily)
    {
        if (StringUtils.isEmpty(daily))
        {
            return StringUtils.EMPTY;
        }
        return String.format("%s-%s-%s", daily.substring(0, 4), daily.substring(4, 6), daily.substring(6));
    }

    public static int toIntDaily(Date date)
    {
        String fmt = format(date, "yyyyMMdd");
        return NumberUtils.toInt(fmt);
    }

    public static int toIntMonth(Date date)
    {
        String fmt = format(date, "yyyyMM");
        return NumberUtils.toInt(fmt);
    }

    public static Date localDate(Date date,int i)
    {
        Calendar tempCal = Calendar.getInstance();
        tempCal.setTime(date);
        tempCal.add(tempCal.DATE,i);
        return tempCal.getTime();
    }
}
