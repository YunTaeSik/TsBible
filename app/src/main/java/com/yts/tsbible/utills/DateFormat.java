package com.yts.tsbible.utills;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormat {
    public final static String OFFERING_DATE_FORMAT = "MM.dd";

    public final static String DATE_FORMAT = "yyyy-MM-dd (E)";

    public final static String FULL_FORMAT = "yyyy-MM-dd (E) a hh:mm";

    public static String getDate(long date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date d = new Date(date);
        return formatter.format(d);
    }

    public static Calendar getCalendar(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    public static boolean isTodayVerseRefresh(long time) {
        Calendar current = Calendar.getInstance();
        GregorianCalendar curreuntCalendar = new GregorianCalendar(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DATE), 0, 0, 0);

        Calendar refreshCal = Calendar.getInstance();
        refreshCal.setTimeInMillis(time);

        GregorianCalendar refreshCalendar = new GregorianCalendar(refreshCal.get(Calendar.YEAR), refreshCal.get(Calendar.MONTH), refreshCal.get(Calendar.DATE), 0, 0, 0);

        int dday = (int) ((curreuntCalendar.getTimeInMillis() - refreshCalendar.getTimeInMillis()) / 1000 / 3600 / 24);
        if (dday != 0) {
            return true;
        } else {
            return false;
        }
    }
}
