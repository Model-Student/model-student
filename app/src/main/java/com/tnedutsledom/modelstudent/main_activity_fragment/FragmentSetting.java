package com.tnedutsledom.modelstudent.main_activity_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.intro_activitys.SplashActivity;

public class FragmentSetting extends Fragment {

    View v;
    LinearLayout ll_btn_delete_account, ll_btn_change_color, ll_btn_change_voice;

    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance(); //파이어스토어 연결

    public static FragmentSetting newInstance() {
        return new FragmentSetting();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        init();
        setLl_btn_delete_account();
        setLl_btn_change_color();
        setLl_btn_change_voice();
        return v;
    }

    public void setLl_btn_change_color() {
        ll_btn_change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentSettingThemeColor.newInstance());
            }
        });
    }

    public void setLl_btn_change_voice() {
        ll_btn_change_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentSettingNuguVoice.newInstance());
            }
        });
    }

    public void setLl_btn_delete_account() {
        ll_btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
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
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                // 파이어베이스에서 유저정보 삭제
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser().delete();

                // 시작화면으로 돌아가기기
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    void init() {
        ll_btn_delete_account = v.findViewById(R.id.ll_btn_delete_account);
        ll_btn_change_color = v.findViewById(R.id.ll_btn_change_theme_color);
        ll_btn_change_voice = v.findViewById(R.id.ll_btn_change_nugu_voice);
    }
}
