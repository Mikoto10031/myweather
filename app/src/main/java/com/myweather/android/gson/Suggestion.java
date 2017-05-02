package com.myweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称：MyWeather
 * 类描述：生活建议：舒适指数，洗车指数，运动指数
 * 创建人：liang
 * 创建时间：2017/5/2 0002 17:47
 * 修改人：liang
 * 修改时间：2017/5/2 0002 17:47
 * 修改备注：
 */
public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public CarWash carWash;
    public Sport sport;
    public class Comfort{
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
