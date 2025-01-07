package com.superliga.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.sql.Date;

/**
 *
 * @author ruteg
 */
public class DateFormatterConverter {
    
    public static LocalDate convertDateToLocalDate(Date dt) {

        // Creating Instant instance
        Instant instant = dt.toInstant();
        // Creating ZonedDateTime instance
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        // Creating LocalDate instance
        LocalDate localDate = zdt.toLocalDate();
        
        

        return localDate;
    }

    public static Date convertLocalDateToDate(LocalDate localDt) {
        
        //Creating ZonedDateTime instance
        ZonedDateTime zdt = localDt.atStartOfDay(ZoneId.systemDefault());
        //Creating Instant instance
        Instant instant = zdt.toInstant();
        //Creating Date instance using instant instance.
        Date date = Date.valueOf(localDt);

        return date;
    }
    
}
