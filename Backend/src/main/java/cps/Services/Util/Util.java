package cps.Services.Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static Timestamp getTimestampFromString(String time) {
        try {
            System.out.println(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = sdf.parse(time);
            System.out.println(date.toString());
            System.out.println(new Timestamp(date.getTime()));
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getDateTime(Timestamp ts) {
        Date date = new Date(ts.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return sdf.format(date);
    }

}
