package us.kardol.amazon.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Guillermo Kardolus
 */
public class TimeStamp {
    // Generate an ISO 8601 timestamp (YYYY-MM-DDThh:mm:ssZ)
    public String convert(Date d){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        return df.format(d);
    }
}
