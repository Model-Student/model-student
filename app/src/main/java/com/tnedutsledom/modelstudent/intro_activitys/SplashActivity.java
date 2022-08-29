package com.tnedutsledom.modelstudent.intro_activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;

public class SplashActivity extends AppCompatActivity {
    //Splash FadeIn 애니메이션
    Animation fadeInAnim,noneAnim,fadeoutAnim;
    //Splash 텍스트 뷰
    TextView tv_logo,tv_small_text;
    //전체를 감싸는 레이아웃
    LinearLayout ll_splash_layout;
    SharedPreferences preferences;
    boolean already_account = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        delayed();
    }

    void init(){
        //Splash FadeIn 애니메이션 연결
        fadeInAnim = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        noneAnim = AnimationUtils.loadAnimation(this,R.anim.none_anim);
        fadeoutAnim = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        //Splash 텍스트 뷰 연결
        tv_logo = findViewById(R.id.tv_logo);
        tv_small_text = findViewById(R.id.tv_small_text);
        //Splash 레이아웃 연결
        ll_splash_layout = findViewById(R.id.ll_splash_layout);
        ll_splash_layout.startAnimation(fadeInAnim);

        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
    }
    //화면전환 딜레이 주기
    void delayed(){
        //딜레이 후 화면전환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //애니메이션 초기화
                ll_splash_layout.startAnimation(noneAnim);

                tv_logo.setVisibility(View.VISIBLE);
                tv_logo.startAnimation(fadeInAnim);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //애니메이션 초기화
                tv_logo.startAnimation(noneAnim);

                tv_small_text.setVisibility(View.VISIBLE);
                tv_small_text.startAnimation(fadeInAnim);
            }
        }, 2500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //작은 텍스트 애니메이션 초기화
                tv_small_text.startAnimation(noneAnim);
                firstTimeCheck();
            }
        }, 4000);
    }

    // preferences에 already_account값에 따라 최초실행인지 아닌지 판단
    // 계정삭제기능 구현시 db유저 정보를 날리고 already_account를 false로 저장하도록 해야함
    void firstTimeCheck() {
        already_account = preferences.getBoolean("already_account", false);
        if (already_account) {
            Intent intent_view_change = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent_view_change);
            intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        } else {
            Intent intent_view_change = new Intent(SplashActivity.this, UserSelectActivity.class);
            startActivity(intent_view_change);
            intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
    }
}
