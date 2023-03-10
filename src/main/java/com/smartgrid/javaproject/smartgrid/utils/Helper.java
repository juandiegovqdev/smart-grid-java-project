package com.smartgrid.javaproject.smartgrid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static Date stringToDate(String timestamp) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(Long.parseLong(timestamp));
    }

    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }
}
