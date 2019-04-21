package it.sevenbits.core.other;

import java.time.Instant;

/**
 * Class for get current time
 */
public final class Time {

    private Time() {}

    public static String getCurrentTime() {
        //SimpleDateFormat date = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        //date.setTimeZone(TimeZone.getTimeZone("UTC"));
        return Instant.now().toString();
    }

}
