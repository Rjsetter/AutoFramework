package cn.yq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTools {
    //用来储存星期对应的数字
    public static final Map<String, Integer> day2number = new HashMap<String, Integer>();

    static {
        day2number.put("星期日", 0);
        day2number.put("星期一", 1);
        day2number.put("星期二", 2);
        day2number.put("星期三", 3);
        day2number.put("星期四", 4);
        day2number.put("星期五", 5);
        day2number.put("星期六", 6);
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int getWeek(int year, int month, int day) {
        String date = year + "-" + month + "-" + 1;
        String weekday = DateTools.dateToWeek(date);
        int num = DateTools.day2number.get(weekday);
        int result = day + num;
//        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(DateTools.dateToWeek("2013-8-25"));
        DateTools.getWeek(2013, 8, 25);
    }
}
