package com.tass.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTimeUtils {



    public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT);

    public static String convertDefaultFormat(Date date){
        try {
            return DEFAULT_DATE_FORMAT.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
