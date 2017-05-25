package com.myweather.android.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.myweather.android.db.City;
import com.myweather.android.db.County;
import com.myweather.android.db.Province;
import com.myweather.android.gson.NewsList;
import com.myweather.android.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：MyWeather
 * 类描述：解析JSON数据(省市县、天气),并保存省市县数据到db
 * 创建人：liang
 * 创建时间：2017/5/1 0001 11:39
 * 修改人：liang
 * 修改时间：2017/5/1 0001 11:39
 * 修改备注：
 */
public class Utility {
    /**
     * 解析JSON的省份信息,保存到数据库
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces=new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++){
                    JSONObject provicneObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provicneObject.getString("name"));
                    province.setProvinceCode(provicneObject.getInt("id"));
                    province.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析JSON的城市信息,保存到数据库
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCtities=new JSONArray(response);
                for (int i=0;i<allCtities.length();i++){
                    JSONObject cityObject=allCtities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析JSON的县和区信息,保存到数据库
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounties=new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 用GSON解析JSON天气数据，以Weather类的形式返回
     */
    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather5");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            Weather weather=new Weather();
            weather=new Gson().fromJson(weatherContent,Weather.class);
            return weather;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * yong Gson解析新闻数据，以NewsList类的形式返回
     * @param response
     * @return
     */
    public static NewsList handleNewsResponse(String response) {
        try {
            Gson gson = new Gson();
            NewsList newsList = gson.fromJson(response, NewsList.class);
            return newsList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
