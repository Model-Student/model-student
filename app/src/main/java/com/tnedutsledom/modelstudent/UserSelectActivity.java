package com.tnedutsledom.modelstudent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserSelectActivity extends AppCompatActivity {
    ImageView iv_user_select_child,iv_user_select_parent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        init();
        onClick();
    }
    void init(){
        iv_user_select_child= findViewById(R.id.iv_user_select_child);
        iv_user_select_parent = findViewById(R.id.iv_user_select_parent);
    }
    void onClick(){
        iv_user_select_child.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent_view_change = new Intent(getApplicationContext(),SignUpActivity.class);
                intent_view_change.putExtra("User_select","child");
                startActivity(intent_view_change);
                intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
                return false;
            }
        });

        iv_user_select_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent_view_change = new Intent(getApplicationContext(),SignUpActivity.class);
                intent_view_change.putExtra("User_select","parent");
                startActivity(intent_view_change);
                intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
                return false;
            }
        });
    }
}
