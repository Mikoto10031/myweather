package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称：MyWeather
 * 类描述：新闻的List类，装载新闻
 * 创建人：liang
 * 创建时间：2017/5/25 0025 16:11
 * 修改人：liang
 * 修改时间：2017/5/25 0025 16:11
 * 修改备注：
 */
public class NewsList {
    public String error_code;
    @SerializedName("result")
    public List<News> NewsList;
}
