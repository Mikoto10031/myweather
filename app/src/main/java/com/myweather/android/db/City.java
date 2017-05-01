package com.myweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * 项目名称：MyWeather
 * 类描述：数据库的城市表
 * 创建人：liang
 * 创建时间：2017/5/1 0001 10:28
 * 修改人：liang
 * 修改时间：2017/5/1 0001 10:28
 * 修改备注：
 */
public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;
    public int getId(){
        return id;
    }
    public String getCityName(){
        return cityName;
    }
    public int getCityCode(){
        return cityCode;
    }
    public int getProvinceId(){
        return provinceId;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setCityName(String cityName){
        this.cityName=cityName;
    }
    public void setCityCode(int cityCode){
        this.cityCode=cityCode;
    }
    public void setProvinceId(int provinceId){
        this.provinceId=provinceId;
    }
}
