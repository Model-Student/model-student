package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tnedutsledom.modelstudent.intro_activitys.SplashActivity;
import com.tnedutsledom.modelstudent.main_activity_fragment.FragmentHelp;
import com.tnedutsledom.modelstudent.main_activity_fragment.FragmentMain;
import com.tnedutsledom.modelstudent.main_activity_fragment.FragmentSetting;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends FragmentActivity {

    private long backpressedTime = 0;
    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance(); //파이어스토어 연결
    AnimatedBottomBar animatedBottomBar;
    ThemeColorAdaptor colorAdaptor;
    TextView tv_main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fragmentInit();
        firstCheck();
        setActivityTheme();
        setBtnFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setActivityTheme();
    }

    void firstCheck(){
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("checkFirst",true);
        editor.commit();
    }

    void setBtnFragment() {
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }

            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                switch (newTab.getId()) {
                    case R.id.menu_help:
                        FragmentHelp fragment_help = new FragmentHelp();
                        replaceFragment(fragment_help);
                        break;
                    case R.id.menu_home:
                        FragmentMain fragment_main = new FragmentMain();
                        replaceFragment(fragment_main);
                        break;
                    case R.id.menu_setting:
                        FragmentSetting fragment_setting = new FragmentSetting();
                        replaceFragment(fragment_setting);
                        break;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_container, fragment).commit();
    }

    public void setActivityTheme() {
        colorAdaptor.setBottomBarColor(animatedBottomBar);
        colorAdaptor.setViewColorTheme(tv_main_title);
    }


    void init() {
        colorAdaptor = ThemeColorAdaptor.getInstance(getApplicationContext());
        animatedBottomBar = findViewById(R.id.main_bottom_bar);
        tv_main_title = findViewById(R.id.tv_main_title);
    }

    void fragmentInit() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentMain fragment_main = new FragmentMain();
        transaction.replace(R.id.fl_fragment_container, fragment_main);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            animatedBottomBar.selectTabAt(1, true);
            FragmentMain fragment_main = new FragmentMain();
            replaceFragment(fragment_main);
            backpressedTime = System.currentTimeMillis();
            FancyToast.makeText(this,"한번 더 누르시면 종료됩니다.", FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }
}