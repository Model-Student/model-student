package com.tnedutsledom.modelstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
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
import com.tnedutsledom.modelstudent.intro_activitys.SplashActivity;
import com.tnedutsledom.modelstudent.main_activity_fragment.FragmentMain;

public class MainActivity extends FragmentActivity {


    ImageView btn_delete_sp_TEST, iv_btn_home;
    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance(); //파이어스토어 연결

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fragmentInit();
        firstCheck();
        TEST_DELETE_USER_DATA();
        setBtnFragment();

    }

    void TEST_DELETE_USER_DATA() {
        btn_delete_sp_TEST = findViewById(R.id.btn_test_sp_delete);
        btn_delete_sp_TEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("user_info",MODE_PRIVATE);
                //파이어베이스의 유저정보 삭제
                firebase_firestore.collection("model_student").document(preferences.getString("email", ""))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("파이어베이스 유저정보 삭제 여부", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("파이어베이스 유저정보 삭제 여부", "Error deleting document", e);
                            }
                        });
                // SharedPreferences의 유저정보 삭제
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                // 구글로그인 객체에서 정보 삭제
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                // 시작화면으로 돌아가기기
               Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                FragmentMain fragment_main = new FragmentMain();
                transaction.replace(R.id.fl_fragment_container, fragment_main);
                transaction.commit();
            }
        });
    }


    void init() {
        iv_btn_home = findViewById(R.id.iv_btn_home);
    }

    void fragmentInit() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentMain fragment_main = new FragmentMain();
        transaction.replace(R.id.fl_fragment_container, fragment_main);
        transaction.commit();
    }
}