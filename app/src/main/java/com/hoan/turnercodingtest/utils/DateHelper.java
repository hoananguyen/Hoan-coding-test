package com.hoan.turnercodingtest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hoan on 10/12/2016.
 */

public class DateHelper {
    private DateHelper() {

    }

    public static String getDate(long unixTime, String gmtTime, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(gmtTime));
        return simpleDateFormat.format(new Date(unixTime * 1000));
    }

    public static boolean isTomorrow(long unixTime, String gmtTime) {
        return numberOfDayFromToday(unixTime, gmtTime) == 1;
    }

    public static int numberOfDayFromToday(long unixTime, String gmtTime) {
        Logger.e("DateHelper", "numberOfDayFromToday\n" + unixTime + "\n" + (System.currentTimeMillis() / 1000));
        if (System.currentTimeMillis() > unixTime * 1000) return -1;

        String today = getDate(System.currentTimeMillis() / 1000, gmtTime, "EEE");
        int todayAsInteger = convertWeekDayToInteger(today);
        String dayOfTheWeek = getDate(unixTime, gmtTime, "EEE");
        int dayOfTheWeekAsInteger = convertWeekDayToInteger(dayOfTheWeek);
        Logger.e("DateHelper", "today = " + today + " todayAsInteger = " + todayAsInteger
                + " dayOfTheWeek = " + dayOfTheWeek + " dayOfTheWeekAsInteger = " + dayOfTheWeekAsInteger);
        int difference = dayOfTheWeekAsInteger - todayAsInteger;
        return difference >= 0 ? difference : difference + 7;
    }

    public static int convertWeekDayToInteger(String weekDay)
    {
        int result = -1;
        if ("Sun".equals(weekDay))
        {
            result = 1;
        }
        else if ("Mon".equals(weekDay))
        {
            result = 2;
        }
        else if ("Tue".equals(weekDay))
        {
            result = 3;
        }
        else if ("Wed".equals(weekDay))
        {
            result = 4;
        }
        else if ("Thu".equals(weekDay))
        {
            result = 5;
        }
        else if ("Fri".equals(weekDay))
        {
            result = 6;
        }
        else if ("Sat".equalsIgnoreCase(weekDay))
        {
            result = 7;
        }

        return result;
    }
}
