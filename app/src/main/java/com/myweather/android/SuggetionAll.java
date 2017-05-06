package com.myweather.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuggetionAll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String briefinfo=intent.getStringExtra("briefinfo");
        String info=intent.getStringExtra("info");
        String infoTitle=intent.getStringExtra("title");
        Log.d("Main",info);
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
        setContentView(R.layout.suggetion_all);
        TextView textViewTitle=(TextView) findViewById(R.id.suggestion_all_title);
        textViewTitle.setText(infoTitle);
        TextView textViewInfo=(TextView) findViewById(R.id.suggestionInfo);
        textViewInfo.setText(info);
        TextView textViewBrief=(TextView) findViewById(R.id.suggestionInfoBrief);
        textViewBrief.setText(briefinfo);
        Button back=(Button) findViewById(R.id.suggestion_all_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
