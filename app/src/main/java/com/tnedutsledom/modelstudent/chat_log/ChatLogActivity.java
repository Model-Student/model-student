package com.tnedutsledom.modelstudent.chat_log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.house_work.WorkListViewAdaptor;

import java.util.ArrayList;

public class ChatLogActivity extends AppCompatActivity {

    ListView lv_chat;
    ArrayList<ChatLogItem> chat_log_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);
        init();
        test();
        updateListView();
    }

    void test() {
        chat_log_list.add(new ChatLogItem("안녕하세요.", "AM 5:58", "nugu"));
        chat_log_list.add(new ChatLogItem("안녕하세요.", "AM 5:58", "nugu"));
        chat_log_list.add(new ChatLogItem("만나서 반가워요.", "AM 5:58", "nugu"));
        chat_log_list.add(new ChatLogItem("반가워 nugu야!", "AM 5:58", "kid"));
        chat_log_list.add(new ChatLogItem("저도 반가워요.", "AM 5:58", "nugu"));
        chat_log_list.add(new ChatLogItem("가장 높은산이 어디야?", "AM 5:58", "kid"));
        chat_log_list.add(new ChatLogItem("신디리댕동댕민둥산이 가장 높아요.", "AM 5:59", "nugu"));
    }

    void updateListView() {
        final ChatLogAdaptor adapter = new ChatLogAdaptor(ChatLogActivity.this, chat_log_list);
        lv_chat.setAdapter(adapter);
        lv_chat.setSelection(adapter.getCount() - 1);
    }

    void init() {
        lv_chat = findViewById(R.id.lv_chat);
        chat_log_list = new ArrayList<>();
    }
}