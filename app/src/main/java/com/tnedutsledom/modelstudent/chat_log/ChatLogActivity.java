package com.tnedutsledom.modelstudent.chat_log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.house_work.WorkListViewAdaptor;

import java.util.ArrayList;

public class ChatLogActivity extends AppCompatActivity {

    ListView lv_chat;                       // 대화가 표시될 리스트뷰
    ArrayList<ChatLogItem> chat_log_list;   // 대화 아이템 리스트
    private SharedPreferences preferences;  // 유저 정보가 저장되어있는 SharedPreferences

    //파이어스토어 연결
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    CollectionReference ColRef;             //Document를 모두 참조


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        init();
        addDateItem();
        getToFirebase();
        updateListView();
    }

    // 어뎁터에서 리스트 아이템중 첫번째 아이템은 시간표시로 띄우기 때문에 임시 아이템을 추가
    void addDateItem() {
        chat_log_list.add(new ChatLogItem("temp", "temp", "temp"));
    }

    // 리스트뷰 상태 업데이트
    void updateListView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ChatLogAdaptor adapter = new ChatLogAdaptor(ChatLogActivity.this, chat_log_list);
                lv_chat.setAdapter(adapter);
                lv_chat.setSelection(adapter.getCount() - 1);
            }
        }, 1000);
    }

    // 객체, 변수 초기화
    void init() {
        lv_chat = findViewById(R.id.lv_chat);
        chat_log_list = new ArrayList<>();
        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
        ColRef = firebaseFirestore.collection("model_student").document(preferences.getString("email", "")).collection("ChatLog");
    }

    // 파이어베이스에서 순서대로 받아와서 아이템 리스트에 추가해주는 코드
    void getToFirebase() {
        ColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        chat_log_list.add(new ChatLogItem(document.getString("chat_data"), document.getString("time"), document.getString("nugu_or_kid")));
                    }
                } else {
                    //실패했을 경우
                }
            }
        });
    }
}