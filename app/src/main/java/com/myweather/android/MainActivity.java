package com.myweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 如果本地有了数据，表明已经请求过，那么直接解析本地的天气数据显示
         */
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getString("weather",null)!=null){
            Intent intent=new Intent(this,WeatherAcitivity.class);
            startActivity(intent);
            finish();
        }
    }
}
