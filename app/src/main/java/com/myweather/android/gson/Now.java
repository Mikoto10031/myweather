package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：MyWeather
 * 类描述：实时天气，温度、天气状况
 * 创建人：liang
 * 创建时间：2017/5/2 0002 17:29
 * 修改人：liang
 * 修改时间：2017/5/2 0002 17:29
 * 修改备注：
 */
public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    @SerializedName("fl")
    public String sensibleTemp;
    @SerializedName("hum")
    public String RelativeHumidity;
    @SerializedName("pres")
    public String airpres;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
