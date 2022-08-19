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

    ListView lv_chat;
    ArrayList<ChatLogItem> chat_log_list;
    private SharedPreferences preferences;// 비밀번호 내부에 저장

    //파이어스토어 연결
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    CollectionReference ColRef; //Document를 모두 참조할 때


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        init();
        popUpDate();
        getToFirebase();
        updateListView();
    }

    void popUpDate() {
        chat_log_list.add(new ChatLogItem("temp", "temp", "temp"));
    }

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

    void init() {
        lv_chat = findViewById(R.id.lv_chat);
        chat_log_list = new ArrayList<>();
        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
        ColRef = firebaseFirestore.collection("model_student").document(preferences.getString("email", "")).collection("chat_log");
    }

    void getToFirebase() {
        ColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("Document 이름과 들어있는 값 모두", document.getId() + " => " + document.getData());
//                        Log.d("나이 값 보기",String.valueOf(document.get("age")));
                        chat_log_list.add(new ChatLogItem(document.getString("chat_data"), document.getString("time"), document.getString("nugu_or_kid")));
                        Log.d("리스트 사이즈 : ", String.valueOf(chat_log_list.size()));
                    }
                } else {
                    //실패했을 경우
                }
            }
        });
    }
}