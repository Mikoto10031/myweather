package com.myweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * 项目名称：MyWeather
 * 类描述：
 * 创建人：liang
 * 创建时间：2017/5/1 0001 10:19
 * 修改人：liang
 * 修改时间：2017/5/1 0001 10:19
 * 修改备注：
 */
public class Province extends DataSupport{
    private int id;
    private String provinceName;
    private int provinceCode;
    public int getId(){
        return id;
    }
    public String getProvinceName(){
        return provinceName;
    }
    public int getProvinceCode(){
        return provinceCode;
    }
    public void setId(int id){
        this.id=id;
    }
    public void setProvinceName(String provinceName){
        this.provinceName=provinceName;
    }
    public void setProvinceCode(int provinceCode){
        this.provinceCode=provinceCode;
    }
}
