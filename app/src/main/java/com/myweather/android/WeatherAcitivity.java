package com.myweather.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.myweather.android.gson.Weather;
import com.myweather.android.util.HttpUtil;
import com.myweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherAcitivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView nowDegree;
    private TextView nowWeatherInfo;
    private LinearLayout forecastLayout;
    private TextView aqi;
    private TextView pm25;
    private TextView suggestionComfort;
    private TextView suggestionCarWash;
    private TextView suggestionSport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        /**
         * 又到了恐怖的控件初始化时间
         */
        weatherLayout=(ScrollView) findViewById(R.id.weather_layout);
        titleCity=(TextView) findViewById(R.id.title_city);
        titleUpdateTime=(TextView) findViewById(R.id.title_updatetime);
        nowDegree=(TextView) findViewById(R.id.now_degree);
        nowWeatherInfo=(TextView) findViewById(R.id.now_weather_info);
        forecastLayout=(LinearLayout) findViewById(R.id.forecast_layout);
        aqi=(TextView) findViewById(R.id.aqi);
        pm25=(TextView) findViewById(R.id.pm25);
        suggestionComfort=(TextView) findViewById(R.id.suggestion_comfort);
        suggestionCarWash=(TextView) findViewById(R.id.suggestion_car_wash);
        suggestionSport=(TextView) findViewById(R.id.suggestion_sport);
        /**
         * 用shredPreferences存储查询天气返回的JSON，优先查询本地JSON，然后才请求天气数据
         */
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherJsonString = preferences.getString("weather",null);
        if(weatherJsonString!=null){
            Weather weather= Utility.handleWeatherResponse(weatherJsonString);
            /**
             * 初始化各个控件
             */
        showWeather(weather);
        }
        else {
            String weatherId=getIntent().getStringExtra("weatherId");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeatherFromApi(weatherId);
        }
    }
    /**
     * 网上查询天气数据
     */
    public void requestWeatherFromApi(String weatherId){
        String ApiUrl="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key=fc99c25bcb6b448cab14ce39790e84b0";
        HttpUtil.sendOkHttpRequest(ApiUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherAcitivity.this, "加载失败了", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData=response.body().string();
                final Weather weather=Utility.handleWeatherResponse(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null&&weather.status.equals("ok")){
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherAcitivity.this).edit();
                            editor.putString("weather",responseData);
                            editor.apply();
                            showWeather(weather);
                        }
                        else {
                            Toast.makeText(WeatherAcitivity.this, "加载失败了", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    /**
     * 利用加载好的Weather初始化控件
     */
    public void showWeather(Weather weather){

    }
}
