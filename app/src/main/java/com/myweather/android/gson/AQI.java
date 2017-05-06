package com.myweather.android.gson;

/**
 * 项目名称：MyWeather
 * 类描述：空气质量指数
 * 创建人：liang
 * 创建时间：2017/5/2 0002 13:31
 * 修改人：liang
 * 修改时间：2017/5/2 0002 13:31
 * 修改备注：
 */
public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;//空气质量指数
        public String pm25;//pm2.5
        public String qlty;//空气等级
    }
}
