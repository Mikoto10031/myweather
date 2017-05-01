package com.myweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * 项目名称：MyWeather
 * 类描述：数据库的县/区表
 * 创建人：liang
 * 创建时间：2017/5/1 0001 10:36
 * 修改人：liang
 * 修改时间：2017/5/1 0001 10:36
 * 修改备注：
 */
public class County extends DataSupport {
    private int id;
    private String countyName;
    private String weatherId;
    private int cityId;
    public int getId(){
        return id;
    }
    public String getCountyName(){
        return countyName;
    }
    public String getWeatherId(){
        return weatherId;
    }
    public int getCityId(){
        return cityId;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setCountyName(String countyName){
        this.countyName=countyName;
    }
    public void setWeatherId(String weatherId){
        this.weatherId=weatherId;
    }
    public void setCityId(int cityId){
        this.cityId=cityId;
    }
}
