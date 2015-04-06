package utils;

import java.text.SimpleDateFormat;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
    public static SimpleDateFormat getSecondFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
