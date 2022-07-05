package com.tnedutsledom.modelstudent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tnedutsledom.modelstudent.FirebaseAdapter.ChildAdapter;
import com.tnedutsledom.modelstudent.FirebaseAdapter.ParentAdapter;

public class SignUpActivity extends AppCompatActivity {

    EditText et_sigh_up_name_text,et_sigh_up_password_text,
            et_sigh_up_first_number_text,et_sigh_up_second_number_text,et_sigh_up_third_number_text; //사용자 작성 부분
    Button bt_sign_up_login; //로그인 버튼
    String parent_or_child = "child";// 부모인지 아이인지 확인
    String phone_number = "";//전화번호
    private FirebaseFirestore firebase_firestore = FirebaseFirestore.getInstance();//파이어스토어 연결

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getInfo();
        init();
        onClick();
        setEtLengthLimit();
    }
    void getInfo(){
        //전 엑티비티에서 값 가져오기
        Intent get_intent = getIntent();
        parent_or_child = get_intent.getStringExtra("User_select");
    }

    void init(){
        et_sigh_up_name_text = findViewById(R.id.et_sigh_up_name_text);
        et_sigh_up_password_text = findViewById(R.id.et_sigh_up_password_text);
        et_sigh_up_first_number_text = findViewById(R.id.et_sigh_up_number_text_1);
        et_sigh_up_second_number_text = findViewById(R.id.et_sigh_up_number_text_2);
        et_sigh_up_third_number_text = findViewById(R.id.et_sigh_up_number_text_3);
        bt_sign_up_login = findViewById(R.id.bt_sign_up_login);

        //글자 길이 지정
        InputFilter lengthFilter = new InputFilter.LengthFilter(4);
        InputFilter[] filters = new InputFilter[]{lengthFilter};
        et_sigh_up_name_text.setFilters(filters);
    }
    void onClick(){
        bt_sign_up_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_sigh_up_name_text.getText().toString().equals("")
                        ||et_sigh_up_password_text.getText().toString().equals("")
                        ||et_sigh_up_first_number_text.getText().toString().equals("")
                        ||et_sigh_up_second_number_text.getText().toString().equals("")
                        ||et_sigh_up_third_number_text.getText().toString().equals("")){
                    //공백 입력칸이 하나라도 있을 시
                    Toast.makeText(SignUpActivity.this,"입력값을 확인해주세요",Toast.LENGTH_SHORT).show();

                }else {
                    if (parent_or_child == "parent"){
                        //부모의 폰에서 올라가는 거
                        uploadToFirebaseParent(et_sigh_up_name_text.getText().toString());
                    }else {
                        //자녀의 폰에서 올라가는 거
                        uploadToFirebaseChild(et_sigh_up_name_text.getText().toString(),phone_number);
                    }

                    Intent intent_view_change = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent_view_change);
                    intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }
                }
        });
    }
    void uploadToFirebaseParent(String name){
        //부모의 폰에서 올라가는 거
        ParentAdapter parent_adapter = new ParentAdapter(name);
        firebase_firestore.collection("User").document(et_sigh_up_password_text.getText().toString())
                .collection(parent_or_child).document("info").set(parent_adapter);
    }
    void uploadToFirebaseChild(String name,String phone_number){
        //자녀의 폰에서 올라가는 거
        ChildAdapter childAdapter = new ChildAdapter(name,phone_number);
        firebase_firestore.collection("User").document(et_sigh_up_password_text.getText().toString())
                .collection(parent_or_child).document("info").set(childAdapter);
    }

    // 전화번호 글자수에따라 이벤트 발생
    void setEtLengthLimit() {
        et_sigh_up_first_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sigh_up_first_number_text.getText().toString();
                if (text_tmp.length() >= 3) {
                    et_sigh_up_second_number_text.requestFocus();
                    phone_number += text_tmp;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_sigh_up_second_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sigh_up_second_number_text.getText().toString();
                if (text_tmp.length() >= 4) {
                    et_sigh_up_third_number_text.requestFocus();
                    phone_number += text_tmp;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_sigh_up_third_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text_tmp = et_sigh_up_third_number_text.getText().toString();
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


//    editText가 포커스되고있을때 다른부분 터치시 화면 내려감감
   @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }
}
