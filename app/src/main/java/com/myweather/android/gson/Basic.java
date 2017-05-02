package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：MyWeather
 * 类描述：信息的基本情况，城市、获取天气的Id、更新时间
 * 创建人：liang
 * 创建时间：2017/5/2 0002 13:24
 * 修改人：liang
 * 修改时间：2017/5/2 0002 13:24
 * 修改备注：
 */
public class Basic {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public  class Update{
        @SerializedName("loc")
        public String updateTime;
    }
}
