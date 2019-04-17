package it.sevenbits.core.other;

import java.time.Instant;

public final class Helper {

    private Helper() {}

    public static String getCurrentTime() {
        //SimpleDateFormat date = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        //date.setTimeZone(TimeZone.getTimeZone("UTC"));
        return Instant.now().toString();
    }

}
