package com.akatsuki.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by think on 2018/5/12.
 */
public class DateApplication {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // 获得本周一与当前日期相差的天数
    public static  int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        return  -6-dayOfWeek;
    }

    // 获得当前周- 周a的日期
    public static  String getCurrentMonday(int a) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus+a);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }
}

