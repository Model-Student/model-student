package com.tnedutsledom.modelstudent.main_activity_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;
import com.tnedutsledom.modelstudent.faq.FragmentFaq;
import com.tnedutsledom.modelstudent.intro_activitys.SplashActivity;

public class FragmentSetting extends Fragment {

    View v, v_theme;
    LinearLayout ll_btn_delete_account, ll_btn_change_color, ll_btn_change_voice, ll_btn_change_child_name, ll_btn_faq;
    TextView tv_user_title;
    ImageView[] iv_arrow;

    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance(); //파이어스토어 연결
    SharedPreferences preferences;
    ThemeColorAdaptor colorAdaptor;


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
        setLl_btn_change_child_name();
        setLl_btn_change_voice();
        setLl_btn_faq();
        setTv_user_title();
        setThemeColor();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setThemeColor();
    }

    void setTv_user_title() {
        tv_user_title.setText(getChildName() + " " + getMotherOrFather());
    }

    String getChildName() {
        String childName = preferences.getString("child_name", "");
        if (childName.length() < 3) {
            return childName;
        } else {
            return childName.substring(1);
        }
    }

    String getMotherOrFather() {
        String mother_or_father = preferences.getString("mother_or_father", "");
        if (mother_or_father.equals("모")) {
            return "엄마";
        } else {
            return "아빠";
        }
    }

    public void setLl_btn_change_child_name() {
        ll_btn_change_child_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNameChangeDialog();
            }
        });
    }

    void setThemeColor() {
        colorAdaptor.setViewColorTheme(v_theme);
        for (int i = 0; i < 5; i++) {
            colorAdaptor.setViewColorTheme(iv_arrow[i]);
        }
    }

    void showNameChangeDialog() {
        Dialog child_name_change_dialog;
        child_name_change_dialog = new Dialog(getActivity());
        child_name_change_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        child_name_change_dialog.setContentView(R.layout.dialog_setting_child_name);
        child_name_change_dialog.show();

        EditText et_value = child_name_change_dialog.findViewById(R.id.et_change_child_name);
        AppCompatButton btn_accept = child_name_change_dialog.findViewById(R.id.btn_change_name_accept);
        AppCompatButton btn_cancel = child_name_change_dialog.findViewById(R.id.btn_change_name_cancel);
        LinearLayout ll_back = child_name_change_dialog.findViewById(R.id.dl_child_name_set_back);

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.setting_change_child_name_shape);
        colorAdaptor.setViewColorBtn(drawable, ll_back);
        colorAdaptor.setViewColorText(et_value);
        colorAdaptor.setViewColorImg(et_value);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_value.getText().toString().equals("")) {
                    DocumentReference ref = firebase_firestore.collection("model_student").document(preferences.getString("email", ""))
                            .collection("SignUp").document(preferences.getString("mother_or_father", ""));

                    ref.update("child_name", et_value.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("아이 이름 변경", "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("아이 이름 변경", "Error updating document", e);
                                }
                            });

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("child_name", et_value.getText().toString());
                    editor.commit();
                    setTv_user_title();

                    child_name_change_dialog.dismiss();

                } else {
                    FancyToast.makeText(getActivity(), "입력값을 확인해주세요.", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                child_name_change_dialog.dismiss();
            }
        });
    }

    public void setLl_btn_change_color() {
        ll_btn_change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(FragmentSettingThemeColor.newInstance());
            }
        });
    }

    public void setLl_btn_change_voice() {
        ll_btn_change_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(FragmentSettingNuguVoice.newInstance());
            }
        });
    }

    public void setLl_btn_faq() {
        ll_btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replaceFragment(FragmentFaq.newInstance());
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
        ll_btn_change_child_name = v.findViewById(R.id.ll_btn_change_child_name);
        preferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        tv_user_title = v.findViewById(R.id.tv_setting_user_title);
        ll_btn_faq = v.findViewById(R.id.ll_btn_faq);
        colorAdaptor = ThemeColorAdaptor.getInstance(getActivity().getApplicationContext());
        v_theme = v.findViewById(R.id.v_theme_color);
        iv_arrow = new ImageView[5];
        iv_arrow[0] = v.findViewById(R.id.iv_arrow1);
        iv_arrow[1] = v.findViewById(R.id.iv_arrow2);
        iv_arrow[2] = v.findViewById(R.id.iv_arrow3);
        iv_arrow[3] = v.findViewById(R.id.iv_arrow4);
        iv_arrow[4] = v.findViewById(R.id.iv_arrow5);
    }
}
