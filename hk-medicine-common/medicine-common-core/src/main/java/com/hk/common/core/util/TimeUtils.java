package com.hk.common.core.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author pengzhengfa
 */
public class TimeUtils {

    /**
     * 获取当前时间 Date类型
     *
     * @return
     */
    public static Date cTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(currentTime.atZone(zoneId).toInstant());
    }

    /**
     * 获取当前时间的String的格式化时间
     *
     * @return
     */
    public static String currentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }
}
