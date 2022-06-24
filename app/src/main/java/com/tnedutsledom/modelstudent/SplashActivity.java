package com.tnedutsledom.modelstudent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //딜레이 후 화면전환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_view_change = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent_view_change);
                finish();
            }
        }, 5000);
    }
}
