package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：MyWeather
 * 类描述：未来天气预报，日期、温度范围、白天天气状况
 * 创建人：liang
 * 创建时间：2017/5/2 0002 18:49
 * 修改人：liang
 * 修改时间：2017/5/2 0002 18:49
 * 修改备注：
 */
public class Forecast {
    public String date;
    @SerializedName("tmp")
    public Temperature temperature;
    @SerializedName("cond")
    public More more;
    public class Temperature{
        public String max;
        public String min;
    }
    public class More{
        @SerializedName("code_d")
        public String imageCode;
        @SerializedName("txt_d")
        public String info;
    }
}
