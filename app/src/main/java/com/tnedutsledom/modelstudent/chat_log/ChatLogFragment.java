package com.tnedutsledom.modelstudent.chat_log;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.main_activity_fragment.FragmentHelp;

import java.util.ArrayList;
import java.util.List;

public class ChatLogFragment extends Fragment {

    View v;
    ListView lv_chat;                       // 대화가 표시될 리스트뷰
    ArrayList<ChatLogItem> chat_log_list;   // 대화 아이템 리스트
    private SharedPreferences preferences;  // 유저 정보가 저장되어있는 SharedPreferences

    //파이어스토어 연결
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    CollectionReference ColRef;             //Document를 모두 참조

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat_log, container, false);
        init();
        addDateItem();
        getToFirebase();
        updateListView();
        return v;
    }


    public static ChatLogFragment newInstance() {
        return new ChatLogFragment();
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
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                final ChatLogAdaptor adapter = new ChatLogAdaptor(getActivity(), chat_log_list);
                lv_chat.setAdapter(adapter);
                lv_chat.setSelection(adapter.getCount() - 1);
            }
        }, 1000);
    }

    // 객체, 변수 초기화
    void init() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        lv_chat = v.findViewById(R.id.lv_chat);
        chat_log_list = new ArrayList<>();
        preferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        ColRef = firebaseFirestore.collection("model_student").document(preferences.getString("email", "")).collection("ChatLog");
    }

    // 파이어베이스에서 순서대로 받아와서 아이템 리스트에 추가해주는 코드
    void getToFirebase() {
        ArrayList<ChatLogItem> tmpList = new ArrayList<ChatLogItem>();
        ColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("chat_data").length() > 20) {
                            String[] tmp = document.getString("chat_data").split("\\. |\\, |! |  ");
                            for (int i = 0; i < tmp.length; i++) {
                                if (document.getId().length() == 5) {
                                    chat_log_list.add(new ChatLogItem(tmp[i], document.getString("time"), document.getString("nugu_or_kid")));
                                    Log.d("아이템 이름", "onComplete: " + document.getId() + "  " + document.getId().length() + " " + i);
                                } else {
                                    tmpList.add(new ChatLogItem(tmp[i], document.getString("time"), document.getString("nugu_or_kid")));
                                    Log.d("아이템 이름", "onComplete: " + document.getId() + "  " + document.getId().length() + " " + i);
                                }
                            }
                        } else {
                            if (document.getId().length() == 5) {
                                chat_log_list.add(new ChatLogItem(document.getString("chat_data"), document.getString("time"), document.getString("nugu_or_kid")));
                                Log.d("아이템 이름", "onComplete: " + document.getId() + "  " + document.getId().length());
                            } else {
                                tmpList.add(new ChatLogItem(document.getString("chat_data"), document.getString("time"), document.getString("nugu_or_kid")));
                                Log.d("아이템 이름", "onComplete: " + document.getId() + "  " + document.getId().length());
                            }
                        }
                    }
                } else {
                    //실패했을 경우
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("리스트 길이", "getToFirebase: " + chat_log_list.size());
                chat_log_list.addAll(tmpList);
                Log.d("리스트 길이22222", "getToFirebase: " + chat_log_list.size());

            }
        }, 800);
    }
}
