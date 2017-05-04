package com.myweather.android.DateToWeek;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 项目名称：MyWeather
 * 类描述：此类用于将日期转换为星期
 * 创建人：liang
 * 创建时间：2017/5/4 0004 22:06
 * 修改人：liang
 * 修改时间：2017/5/4 0004 22:06
 * 修改备注：
 */
public class VeDate {
        public static Date strToDate(String strDate) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        }

    public static String getWeek(String sdate) {
            // 再转换为时间
            Date date = VeDate.strToDate(sdate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
        /**
         *  int hour=c.get(Calendar.DAY_OF_WEEK);
         hour中存的就是星期几了，其范围 1~
         1=星期日 7=星期六，其他类推
         */
        return new SimpleDateFormat("EEEE").format(c.getTime());
        }
    /**
     * 根据一个日期，返回是星期几的字符串
     */
        public static String getWeekStr(String sdate){
            String str = "";
            str = VeDate.getWeek(sdate);
            if("1".equals(str)){
                str = "星期日";
            }else if("2".equals(str)){
                str = "星期一";
            }else if("3".equals(str)){
                str = "星期二";
            }else if("4".equals(str)){
                str = "星期三";
            }else if("5".equals(str)){
                str = "星期四";
            }else if("6".equals(str)){
                str = "星期五";
            }else if("7".equals(str)){
                str = "星期六";
            }
            return str;
        }
}


