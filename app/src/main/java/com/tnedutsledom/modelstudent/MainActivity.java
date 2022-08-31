package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
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

public class MainActivity extends FragmentActivity {

    private long backpressedTime = 0;
    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance(); //파이어스토어 연결
    ImageView iv_btn_setting, iv_btn_home, iv_btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fragmentInit();
        firstCheck();
        setBtnFragment();

    }


    void firstCheck(){
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("checkFirst",true);
        editor.commit();
    }

    void setBtnFragment() {
        iv_btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentMain fragment_main = new FragmentMain();
                replaceFragment(fragment_main);
            }
        });
        iv_btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentHelp fragment_help = new FragmentHelp();
                replaceFragment(fragment_help);
            }
        });

        iv_btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSetting fragment_setting = new FragmentSetting();
                replaceFragment(fragment_setting);
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_container, fragment).commit();
    }




    void init() {
        iv_btn_home = findViewById(R.id.iv_btn_home);
        iv_btn_help = findViewById(R.id.iv_btn_help);
        iv_btn_setting = findViewById(R.id.iv_btn_setting);
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
            backpressedTime = System.currentTimeMillis();
            FancyToast.makeText(this,"한번 더 누르시면 종료됩니다.", FancyToast.LENGTH_SHORT, FancyToast.INFO,false).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }
}