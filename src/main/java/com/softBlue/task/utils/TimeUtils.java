package com.softBlue.task.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static String formatDate(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        return date != null ? formatter.format(date.toInstant()) : "";
    }

    public static Long getNowTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now()).getTime();
    }
}






