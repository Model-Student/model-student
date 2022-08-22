package com.tnedutsledom.modelstudent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tnedutsledom.modelstudent.FirebaseAdapter.ChildAdapter;
import com.tnedutsledom.modelstudent.FirebaseAdapter.ParentAdapter;

public class SignUpActivity extends AppCompatActivity {

    EditText et_sign_up_name_text, et_sign_up_first_number_text, et_sign_up_second_number_text,
            et_sign_up_third_number_text, et_sign_up_child_name_text; // 사용자 작성 부분
    Button bt_sign_up_login; // 로그인 버튼
    RadioGroup rag_sign_up_mother_or_father;
    String phone_number = "";// 전화번호
    String parent_or_child = "parent";
    String user_email;
    String mother_or_father = null;
    LinearLayout LL_sign_up_step2;// step 2
    LinearLayout LL_sign_up_step3_parent, LL_sign_up_step3_child; // 아이,부모 Step3 달라지게
    Animation fade_in, fade_out, none;// 애니메이션
    ImageView iv_sign_up_first_banana, iv_sign_up_second_banana, iv_sign_up_third_banana_parent, iv_sign_up_third_banana_child;// 바나나 이미지
    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance();// 파이어스토어 연결
    private SharedPreferences preferences;// 비밀번호 내부에 저장

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getInfo();
        init();
        setBt_sign_up_login();
        setEtLengthLimit();
        stepEvent();
    }

    void getInfo() {
        //전 엑티비티에서 값 가져오기
        Intent get_intent = getIntent();
        user_email = get_intent.getStringExtra("user_email");
    }

    void init() {
        et_sign_up_name_text = findViewById(R.id.et_sign_up_name_text);
        et_sign_up_first_number_text = findViewById(R.id.et_sign_up_number_text_1);
        et_sign_up_second_number_text = findViewById(R.id.et_sign_up_number_text_2);
        et_sign_up_third_number_text = findViewById(R.id.et_sign_up_number_text_3);
        bt_sign_up_login = findViewById(R.id.bt_sign_up_login);
        et_sign_up_child_name_text = findViewById(R.id.et_sign_up_child_name_text);
        rag_sign_up_mother_or_father = findViewById(R.id.rag_mother_or_father);

        //Step1,2,3
        LL_sign_up_step2 = findViewById(R.id.LL_sign_up_step2);
        LL_sign_up_step3_child = findViewById(R.id.LL_sign_up_step3_child);
        LL_sign_up_step3_parent = findViewById(R.id.LL_sign_up_step3_parent);

        //애니메이션 지정
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        none = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.none_anim);
        //글자 길이 지정
        InputFilter lengthFilter = new InputFilter.LengthFilter(4);
        InputFilter[] filters = new InputFilter[]{lengthFilter};
        et_sign_up_name_text.setFilters(filters);
        //바나나 애니메이션 이미지 뷰 지정
        iv_sign_up_first_banana= findViewById(R.id.iv_sign_up_first_banana);
        iv_sign_up_second_banana = findViewById(R.id.iv_sign_up_second_banana);
        iv_sign_up_third_banana_parent = findViewById(R.id.iv_sign_up_third_banana_parent);
        iv_sign_up_third_banana_child = findViewById(R.id.iv_sign_up_third_banana_child);

    }
    
    void showStep3() {
        //바나나 애니메이션
        iv_sign_up_third_banana_parent.setImageResource(R.drawable.sign_up_banana3_iv);
        iv_sign_up_third_banana_parent.startAnimation(fade_in);
        //두 번째 바나나 퇴장
        iv_sign_up_second_banana.startAnimation(fade_out);

        //질문
        LL_sign_up_step3_child.setVisibility(View.GONE);
        LL_sign_up_step3_parent.startAnimation(fade_in);
        LL_sign_up_step3_parent.setVisibility(View.VISIBLE);

        et_sign_up_child_name_text.requestFocus();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //두 번째 바나나와 세 번째 질문 애니메이션 초기화
                LL_sign_up_step3_parent.startAnimation(none);
                iv_sign_up_third_banana_parent.startAnimation(none);
                iv_sign_up_second_banana.setImageResource(0);
                iv_sign_up_second_banana.startAnimation(none);
            }
        }, 800);
    }

    void setBt_sign_up_login() {
        bt_sign_up_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login_success = false;
                if (et_sign_up_name_text.getText().toString().equals("")
                        || mother_or_father == null) {
                    //공통 질문에 공백 입력칸이 하나라도 있을 시
                    Toast.makeText(SignUpActivity.this, "입력값을 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    if (parent_or_child.equals("parent")) {
                        if (et_sign_up_child_name_text.getText().toString().equals("")) {
                            //부모 전용 입력칸에 공백 입력칸이 하나라도 있을 시
                            Toast.makeText(SignUpActivity.this, "입력값을 확인해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            //부모의 폰에서 올라가는 거
                            Log.d("1111111111", "adsad" + user_email);
                            uploadToFirebaseParent(et_sign_up_name_text.getText().toString(),et_sign_up_child_name_text.getText().toString());
                            login_success = true;
                        }
                    } else {
                        if (et_sign_up_first_number_text.getText().toString().equals("") ||
                                et_sign_up_second_number_text.getText().toString().equals("") ||
                                et_sign_up_third_number_text.getText().toString().equals("")) {
                            //자녀 전용 입력칸에 공백 입력칸이 하나라도 있을 시
                            Toast.makeText(SignUpActivity.this, "입력값을 확인해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            //자녀의 폰에서 올라가는 거
                            uploadToFirebaseChild(et_sign_up_name_text.getText().toString(), phone_number);
                            login_success = true;

                        }
                    }
                }
                if (login_success == true) {
                    //toast 메세지 띄움
                    Toast.makeText(SignUpActivity.this, "ModelStudent에 오신 것을 환영합니다.", Toast.LENGTH_SHORT).show();
                    //내부에 값 저장
                    saveInfoSharedPreferences();
                    //화면전환
                    Intent intent_view_change = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent_view_change);
                    intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        });
    }
    //SharedPreferences 로 앱에 필요한 정보 저장
    void saveInfoSharedPreferences(){
//        SharedPreferences 저장 값
//        키 = "user_info"
//        값 = "mother_or_father" : 사용자가 아빠인지 엄마인지
//        값 = "email" : 유저 이메일
//        값 = "already_account" : 계정이 이미 존재하는지
        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
        //Editor를 preferences에 쓰겠다고 연결
        SharedPreferences.Editor editor = preferences.edit();
        //putString(KEY,VALUE)
        editor.putString("mother_or_father",mother_or_father);
        editor.putString("email",user_email);
        editor.putBoolean("already_account", true);
        //항상 commit & apply 를 해주어야 저장이 된다.
        editor.commit();
    }

    void uploadToFirebaseParent(String name, String child_name) {
        //부모의 폰에서 올라가는 거
        ParentAdapter parent_adapter = new ParentAdapter(name,child_name);
        firebase_firestore.collection("model_student").document(user_email)
                .collection("SignUp").document(mother_or_father).set(parent_adapter);
    }

    void uploadToFirebaseChild(String name, String phone_number) {
        //자녀의 폰에서 올라가는 거
        ChildAdapter childAdapter = new ChildAdapter(name, phone_number);
        firebase_firestore.collection("model_student").document(user_email)
                .collection("SignUp").document(mother_or_father).set(childAdapter);
    }

    // 전화번호 글자수에따라 이벤트 발생
    void setEtLengthLimit() {
        et_sign_up_first_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sign_up_first_number_text.getText().toString();
                if (text_tmp.length() >= 3) {
                    et_sign_up_second_number_text.requestFocus();
                    phone_number += text_tmp;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_sign_up_second_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sign_up_second_number_text.getText().toString();
                if (text_tmp.length() >= 4) {
                    et_sign_up_third_number_text.requestFocus();
                    phone_number += text_tmp;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_sign_up_third_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sign_up_third_number_text.getText().toString();
                if (text_tmp.length() >= 4) {
                    phone_number += text_tmp;
                    hideKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    //Step이 순서대로 나오는 함수
    void stepEvent() {
        //사용자 이름
        et_sign_up_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //천지인 방지 편법(입력할 시간 지연)
                Handler handler_delayed2 = new Handler();
                handler_delayed2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() >= 3) {
                            if (LL_sign_up_step2.getVisibility() != View.VISIBLE) {
                                //바나나 애니메이션
                                iv_sign_up_second_banana.setImageResource(R.drawable.sign_up_banana2_iv);
                                iv_sign_up_second_banana.startAnimation(fade_in);
                                //첫번쨰 바나나 퇴장
                                iv_sign_up_first_banana.startAnimation(fade_out);

                                //질문 애니메이션
                                LL_sign_up_step2.startAnimation(fade_in);
                                LL_sign_up_step2.setVisibility(View.VISIBLE);
                                Handler handler_delayed = new Handler();
                                handler_delayed.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //첫 번쨰 바나나와 두 번째 질문 애니메이션 초기화
                                        LL_sign_up_step2.startAnimation(none);
                                        iv_sign_up_second_banana.startAnimation(none);
                                        iv_sign_up_first_banana.setImageResource(0);
                                        iv_sign_up_first_banana.startAnimation(none);

                                    }
                                }, 800);
                            }
                        }
                    }
                }, 1000);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rag_sign_up_mother_or_father.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rabtn_father:
                        if (mother_or_father == null) {
                            showStep3();
                        }
                        mother_or_father = "부";
                        break;
                    case R.id.rabtn_mother:
                        if (mother_or_father == null) {
                            showStep3();
                        }
                        mother_or_father = "모";
                        break;
                }
            }
        });
        
        //부모 권한일 때 자녀 이름 입력
        et_sign_up_child_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 3){
                    //자녀 이름까지 입력하고 1초뒤 키보드 내려감
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideKeyboard();
                        }
                    }, 1000);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 키보드 숨기기
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //    editText가 포커스되고있을때 다른부분 터치시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }
}
