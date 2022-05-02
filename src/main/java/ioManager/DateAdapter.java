package ioManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter {

    public static boolean isAdapting(String s, String dataPattern){
        SimpleDateFormat parser = new SimpleDateFormat(dataPattern);
        try {
            Date date = parser.parse(s);
        }
        catch(ParseException ex){
            return false;
        }
        return true;
    }
    public static Date adapt(String s, String dataPattern){
        SimpleDateFormat parser = new SimpleDateFormat(dataPattern);
        try {
            Date date = parser.parse(s);
            return date;
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
        return new Date();
    }
}
