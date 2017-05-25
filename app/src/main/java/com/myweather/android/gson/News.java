package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：MyWeather
 * 类描述：新闻的具体内容
 * 创建人：liang
 * 创建时间：2017/5/25 0025 16:02
 * 修改人：liang
 * 修改时间：2017/5/25 0025 16:02
 * 修改备注：
 */
public class News {
    public String title;
    @SerializedName("img")
    public String imgage;
    public String url;
}
