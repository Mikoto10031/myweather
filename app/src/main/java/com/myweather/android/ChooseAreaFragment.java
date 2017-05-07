package com.myweather.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myweather.android.db.City;
import com.myweather.android.db.County;
import com.myweather.android.db.Province;
import com.myweather.android.util.HttpUtil;
import com.myweather.android.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 项目名称：MyWeather
 * 类描述：加载省市县列表，先从本地加载然后从服务器加载
 * 创建人：liang
 * 创建时间：2017/5/1 0001 14:02
 * 修改人：liang
 * 修改时间：2017/5/1 0001 14:02
 * 修改备注：
 */
public class ChooseAreaFragment extends Fragment {
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    /**
     * ListView的适配器
     */
    private ArrayAdapter<String> adapter;
    /**
     * 适配器的List
     */
    private List<String> datalist=new ArrayList<>();
    /**
     * 省份列表
     */
    private List<Province> provinceList;
    /**
     * 城市列表
     */
    private List<City> cityList;
    /**
     * 县/区列表
     */
    private List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 选中的级别
     */
    private int currentLevel;


    /**
     * 为ListView设置适配器
     */
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        titleText=(TextView) view.findViewById(R.id.title_text);
        backButton=(Button) view.findViewById(R.id.back_button);
        listView=(ListView) view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * 当点击了ListView时，显示下一层的数据
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(i);
                    queryCities();
                }
                else if(currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(i);
                    queryCounties();
                }
                /**
                 * 点击到了县/区级，此时就要开始关闭当前界面，显示天气
                 */
                else if(currentLevel==LEVEL_COUNTY){
                    String weatherId=countyList.get(i).getWeatherId();
                    /**
                     * 判断当前界面在MainActivity,那么就跳转显示天气界面
                     */
                    if(getActivity() instanceof MainActivity){
                        Intent intent=new Intent(getActivity(),WeatherAcitivity.class);
                        intent.putExtra("weatherId",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    /**
                     * 判断当前界面界面在WeathereActivity，那么不用跳转，关闭侧拉菜单,刷新天气数据
                     */
                    else if(getActivity() instanceof WeatherAcitivity){
                        WeatherAcitivity acitivity=(WeatherAcitivity) getActivity();
                        acitivity.drawerLayout.closeDrawers();
                        acitivity.swipeRefreshLayout.setRefreshing(true);
                        acitivity.requestWeatherFromApi(weatherId);
                    }
                }
            }
        });
        /**
         * 点击返回按钮时，显示上一层的数据
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_COUNTY){
                    queryCities();
                }
                else if(currentLevel==LEVEL_CITY){
                    queryProvince();
                }
            }
        });
        /**
         * 初始化的数据为省份数据
         */
        queryProvince();
    }
    /**
     * 点击手机的返回键时，也要实现返回按钮的功能(功能未完善)
     */
    /**
     * 查询省份，本地数据库查询→api查询
     */
    private void queryProvince(){
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList= DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            datalist.clear();
            for(Province province:provinceList){
                datalist.add(province.getProvinceName());
            }
            /**
             * 更新适配器，显示查询的内容
             */
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }
        else {
            String address="http://guolin.tech/api/china";
            queryFromApi(address,"province");
        }
    }

    /**
     * 查询城市，本地数据库查询→api查询
     */
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList=DataSupport.where("provinceid=?",String .valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            datalist.clear();
            for(City city:cityList){
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }
        else {
            int provinceCode=selectedProvince.getProvinceCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            queryFromApi(address,"city");
        }
    }
    /**
     * 查询县/区，本地数据库查询→api查询
     */
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityid=?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            datalist.clear();
            for(County county:countyList){
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }
        else {
            int provinceCode=selectedProvince.getProvinceCode();
            int cityCode=selectedCity.getCityCode();
            String address="http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            queryFromApi(address,"county");
        }
    }
    /**
     * api查询
     */
    private void queryFromApi(String address,final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败了", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                boolean result=false;
                if(type.equals("province")){
                    result= Utility.handleProvinceResponse(responseData);
                }
                else if(type.equals("city")){
                    result=Utility.handleCityResponse(responseData,selectedProvince.getId());
                }
                else if(type.equals("county")){
                    result=Utility.handleCountyResponse(responseData,selectedCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvince();
                            }
                            else if("city".equals(type)){
                                queryCities();
                            }
                            else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void  showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("拼命加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void  closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
