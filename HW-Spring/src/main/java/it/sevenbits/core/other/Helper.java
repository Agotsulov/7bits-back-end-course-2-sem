package it.sevenbits.core.other;

import java.time.Instant;

public final class Helper {

    private Helper() {}

    public static String getCurrentTime() {
        return Instant.now().toString();
    }

}
