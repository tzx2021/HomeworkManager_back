package sc.hqu.graduationdesign.homeworkmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tzx
 * @date 2021-04-10 22:53
 */
public class TimeFormatUtil {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String format(Long timeMillions){
        return FORMATTER.format(new Date(timeMillions));
    }

}
