package com.tnedutsledom.modelstudent;

import android.app.Activity;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SplashActivity extends AppCompatActivity {
    //Splash FadeIn 애니메이션
    Animation fadeInAnim,noneAnim,fadeoutAnim;
    //Splash 텍스트 뷰
    TextView tv_logo,tv_small_text;
    //전체를 감싸는 레이아웃
    LinearLayout ll_splash_layout;
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
    void firstTimeCheck(){
        //        최초 실행 여부를 판단 ->>>
        // 기존에 로그인 했던 계정을 확인한다.
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);

        // 로그인 되있는 경우
        if (gsa != null) {
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
//        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
//        boolean checkFirst = pref.getBoolean("checkFirst", false);
//        if(checkFirst==false){
//            // 앱 최초 실행시 하고 싶은 작업
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putBoolean("checkFirst",true);
//            editor.commit();
//
//            Intent intent_view_change = new Intent(SplashActivity.this, UserSelectActivity.class);
//            startActivity(intent_view_change);
//            intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//            finish();
//        }else{
//            // 최초 실행이 아닐때 진행할 작업
//            Intent intent_view_change = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent_view_change);
//            intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//            finish();
//        }
    }
}
