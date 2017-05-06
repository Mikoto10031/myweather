package com.myweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myweather.android.gson.Forecast;
import com.myweather.android.gson.HourlyForecast;
import com.myweather.android.gson.Weather;
import com.myweather.android.util.HttpUtil;
import com.myweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.myweather.android.DateToWeek.VeDate.getWeekStr;
import static com.myweather.android.R.id.suggestion_comfort;

/**
 * 项目名称：MyWeather
 * 类描述：从本地和api加载天气数据并显示
 * 创建人：liang
 * 创建时间：2017/5/3 0001 21:01
 * 修改人：liang
 * 修改时间：2017/5/3 0001 22:01
 * 修改备注：
 */
public class WeatherAcitivity extends AppCompatActivity implements View.OnClickListener{

    private String Key="fc99c25bcb6b448cab14ce39790e84b0";
    private String OtherKey="bc0418b57b2d4918819d3974ac1285d9";
    private Weather infoweather;
    public DrawerLayout drawerLayout;
    public Button choose_county;
    public SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView nowDegree;
    private TextView nowWeatherInfo;
    private LinearLayout forecastLayout;
    private TextView aqi;
    private TextView pm25;
    private TextView qlty;
    private TextView pop;
    private TextView hum;
    private TextView fl;
    private Button suggestionComfort;
    private Button suggestionCarWash;
    private Button suggestionSport;
    private Button suggestionLayer;
    private Button suggestionFlu;
    private Button suggestionClothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 版本号大于21（Android5.0）时，融合状态栏
         */
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        /**
         * 控件初始化,求求你了，给我的肝放个假吧！
         * 吔shi了
         */
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        choose_county=(Button)findViewById(R.id.choose_county);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        weatherLayout=(ScrollView) findViewById(R.id.weather_layout);
        titleCity=(TextView) findViewById(R.id.title_city);
        titleUpdateTime=(TextView) findViewById(R.id.title_updatetime);
        nowDegree=(TextView) findViewById(R.id.now_degree);
        nowWeatherInfo=(TextView) findViewById(R.id.now_weather_info);
        forecastLayout=(LinearLayout) findViewById(R.id.forecast_layout);
        aqi=(TextView) findViewById(R.id.aqi);
        pm25=(TextView) findViewById(R.id.pm25);
        qlty=(TextView) findViewById(R.id.qlty);
        pop=(TextView) findViewById(R.id.pop);
        hum=(TextView) findViewById(R.id.hum);
        fl=(TextView) findViewById(R.id.fl);
        suggestionComfort=(Button) findViewById(suggestion_comfort);
        suggestionCarWash=(Button) findViewById(R.id.suggestion_car_wash);
        suggestionSport=(Button) findViewById(R.id.suggestion_sport);
        suggestionLayer=(Button) findViewById(R.id.layer);
        suggestionFlu=(Button) findViewById(R.id.flu);
        suggestionClothes=(Button) findViewById(R.id.clothes);
        /**
         * 用shredPreferences存储查询天气返回的JSON，优先查询本地JSON，然后才请求天气数据
         */
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherJsonString = preferences.getString("weather",null);
        /**
         * 本地 SharedPreferences非空时，直接加载天气
         */
        final String weatherId;
        if(weatherJsonString!=null){
            Weather weather= Utility.handleWeatherResponse(weatherJsonString);
            infoweather=weather;
            weatherId=weather.basic.weatherId;
            /**
             * 初始化各个控件
             */
        showWeather(weather);
        }
        /**
         * 本地 SharedPreferences为空时，通过传来的weatherId来请求天气数据
         */
        else {
            weatherId=getIntent().getStringExtra("weatherId");
            /**
             * 此处设置了 weatherLayout 不可见，还要记得设置回来
             */
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeatherFromApi(weatherId);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeatherFromApi(weatherId);
            }
        });
        /**
         * 打开侧拉菜单
         */
        choose_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        /**
         * 生活建议的具体内容
         */
        suggestionComfort.setOnClickListener(this);
        suggestionCarWash.setOnClickListener(this);
        suggestionSport.setOnClickListener(this);
        suggestionLayer.setOnClickListener(this);
        suggestionFlu.setOnClickListener(this);
        suggestionClothes.setOnClickListener(this);
    }
    /**
     * 显示生活建议的具体内容，又出Bug了，FA♂Q
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case suggestion_comfort: {
                String briefinfo="\n"+infoweather.suggestion.comfort.briefInfo;
                String info = "\n"+infoweather.suggestion.comfort.info;
                String title="舒适指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                startActivity(intent);
            }
            break;
            case R.id.suggestion_car_wash: {
                String briefinfo="\n"+infoweather.suggestion.carWash.briefInfo;
                String info = "\n"+infoweather.suggestion.carWash.info;
                String title="洗车指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                Log.d("Main",info);
                startActivity(intent);
            }
            break;
            case R.id.suggestion_sport: {
                String briefinfo="\n"+infoweather.suggestion.sport.briefInfo;
                String info = "\n"+infoweather.suggestion.sport.info;
                String title="运动指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                startActivity(intent);
            }
            break;
            case R.id.layer: {
                String briefinfo="\n"+infoweather.suggestion.layer.briefInfo;
                String info = "\n"+infoweather.suggestion.layer.info;
                String title="紫外线指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                startActivity(intent);
            }
            break;
            case R.id.flu: {
                String briefinfo="\n"+infoweather.suggestion.fluence.briefInfo;
                String info = "\n"+infoweather.suggestion.fluence.info;
                String title="感冒指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                startActivity(intent);
            }
            break;
            case R.id.clothes: {
                String briefinfo="\n"+infoweather.suggestion.clothes.briefInfo;
                String info = "\n"+infoweather.suggestion.clothes.info;
                String title="穿衣指数";
                Intent intent=new Intent(WeatherAcitivity.this,SuggetionAll.class);
                intent.putExtra("briefinfo",briefinfo);
                intent.putExtra("info",info);
                intent.putExtra("title",title);
                startActivity(intent);
            }
            break;
            default:
                break;
        }
    }

    /**
     * 网上查询天气数据存到本地并加载天气数据
     */
    public void requestWeatherFromApi(String weatherId){
        /**
         * api接口
         */
        String ApiUrl="https://free-api.heweather.com/v5/weather?city="+weatherId+"&key="+Key;
        HttpUtil.sendOkHttpRequest(ApiUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherAcitivity.this, "加载失败了", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData=response.body().string();
                Log.d("Response",responseData);
                final Weather weather=Utility.handleWeatherResponse(responseData);
                infoweather=weather;
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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    /**
     * 利用加载好的Weather初始化控件
     */
    public void showWeather(Weather weather){
        String cityName=weather.basic.cityName;
        String updateTime=weather.basic.update.updateTime.split(" ")[1];
        String degree="  "+weather.now.temperature+"°";
        String weatherinfo=weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText("更新于 "+updateTime);
        nowDegree.setText(degree);
        nowWeatherInfo.setText(weatherinfo);
        forecastLayout.removeAllViews();
        for(Forecast forecast:weather.forecastList){
            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView forecast_date=(TextView) view.findViewById(R.id.forecast_date);
            TextView forecast_info=(TextView) view.findViewById(R.id.forecast_info);
            TextView forecast_max=(TextView) view.findViewById(R.id.forecast_max);
            TextView forecast_min=(TextView) view.findViewById(R.id.forecast_min);
            ImageView forecast_image=(ImageView) view.findViewById(R.id.forecast_image);
            /**
             * 预报的天气的图标的URL
             */
            String forecast_image_code=forecast.more.imageCode;
            String forecast_image_url="https://cdn.heweather.com/cond_icon/"+forecast_image_code+".png";
            /**
             * 日期转星期,人性化，移除前导0
             */
            String forecast_dateToWeek=getWeekStr(forecast.date);
            String forecast_date_mon=forecast.date.split("-")[1];
            String forecast_date_day=forecast.date.split("-")[2];
            if (forecast_date_mon.charAt(0)=='0') forecast_date_mon=forecast_date_mon.substring(1);
            if (forecast_date_day.charAt(0)=='0') forecast_date_day=forecast_date_day.substring(1);
            /**
             *泽泽称奇，出Bug了，FA♂Q
             */
            forecast_date.setText(forecast_dateToWeek+" "+forecast_date_mon+"/"+forecast_date_day);
            forecast_info.setText(forecast.more.info);
            forecast_max.setText(forecast.temperature.max+"℃");
            forecast_min.setText(forecast.temperature.min+"℃");
            Glide.with(WeatherAcitivity.this).load(forecast_image_url).into(forecast_image);
            forecastLayout.addView(view);
        }
        /**
         * 喜闻乐见，又出Bug了，FA♂Q
         * 返回值类型不匹配，GSON解析错误
         */
        if(weather.aqi!=null){
            aqi.setText(weather.aqi.city.aqi);
            pm25.setText(weather.aqi.city.pm25);
            qlty.setText(weather.aqi.city.qlty);
        }
        if(weather.hourly_forecast!=null){
            HourlyForecast hourlyForecast=weather.hourly_forecast.get(0);
            pop.setText(hourlyForecast.RainfallProbability);
            hum.setText(hourlyForecast.RelativeHumidity);
        }
        if(weather.now!=null){
            fl.setText(weather.now.sensibleTemp);
        }
        String suggestion_comfort="舒适指数\n"+weather.suggestion.comfort.briefInfo;
        String suggestion_carWash="洗车指数\n"+weather.suggestion.carWash.briefInfo;
        String suggestion_sport="运动指数\n"+weather.suggestion.sport.briefInfo;
        String suggestion_layer="紫外指数\n"+weather.suggestion.layer.briefInfo;
        String suggestion_flu="感冒指数\n"+weather.suggestion.fluence.briefInfo;
        String suggestion_clothes="穿衣指数\n"+weather.suggestion.clothes.briefInfo;
        suggestionComfort.setText(suggestion_comfort);
        suggestionCarWash.setText(suggestion_carWash);
        suggestionSport.setText(suggestion_sport);
        suggestionLayer.setText(suggestion_layer);
        suggestionFlu.setText(suggestion_flu);
        suggestionClothes.setText(suggestion_clothes);
        weatherLayout.setVisibility(View.VISIBLE);
        /**
         * 计划功能：
         * 1.显示了天气后启动后台服务，隔一段时间更新一次天气
         * 2.增加每日新闻
         */
    }
}
