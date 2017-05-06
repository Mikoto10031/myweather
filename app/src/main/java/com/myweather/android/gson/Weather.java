package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：MyWeather
 * 类描述：总的JSON数据对应的实体类
 * 创建人：liang
 * 创建时间：2017/5/2 0002 19:18
 * 修改人：liang
 * 修改时间：2017/5/2 0002 19:18
 * 修改备注：
 */
public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Suggestion suggestion;
    public Now now;
    public List<HourlyForecast> hourly_forecast;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
