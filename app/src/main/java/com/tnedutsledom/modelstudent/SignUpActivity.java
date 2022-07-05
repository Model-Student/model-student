package com.tnedutsledom.modelstudent;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText et_sigh_up_name_text,et_sigh_up_password_text,et_sigh_up_number_text; //사용자 작성 부분
    Button bt_sign_up_login; //로그인 버튼
    String name,phone_number;// 데이터베이스에 올릴 값
    boolean parent_child_check = false;// 부모인지 아이인지 확인
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        onClick();
    }

    void init(){
        et_sigh_up_name_text = findViewById(R.id.et_sigh_up_name_text);
        et_sigh_up_password_text = findViewById(R.id.et_sigh_up_password_text);
        et_sigh_up_number_text = findViewById(R.id.et_sigh_up_number_text);
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
                Intent intent_view_change = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent_view_change);
                intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
    }
    void UploadToFirebaseParent(String name){

    }
    void UploadToFirebaseChild(String name,String phone_number){

    }
}
