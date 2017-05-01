package com.myweather.android.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 项目名称：MyWeather
 * 类描述：发送Http请求，得到JSON数据
 * 创建人：liang
 * 创建时间：2017/5/1 0001 11:36
 * 修改人：liang
 * 修改时间：2017/5/1 0001 11:36
 * 修改备注：
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
