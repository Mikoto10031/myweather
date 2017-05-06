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
    @SerializedName("sport")
    public Sport sport;
    @SerializedName("drsg")
    public Clothes clothes;
    @SerializedName("flu")
    public Fluence fluence;
    @SerializedName("uv")
    public Layer layer;
    public class Comfort{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
    public class CarWash{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
    public class Clothes{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
    public class Fluence{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
    public class Layer{
        @SerializedName("brf")
        public String briefInfo;
        @SerializedName("txt")
        public String info;
    }
}
