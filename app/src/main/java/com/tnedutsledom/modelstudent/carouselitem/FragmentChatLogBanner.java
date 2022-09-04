package com.tnedutsledom.modelstudent.carouselitem;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.chat_log.ChatLogFragment;


public class FragmentChatLogBanner extends Fragment {

    LinearLayout ll_chat_log;
    TextView tv_chat_log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_log_banner, container, false);
        ll_chat_log = rootView.findViewById(R.id.btn_main_chat_log);
        ll_chat_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(ChatLogFragment.newInstance());
            }
        });

        tv_chat_log = rootView.findViewById(R.id.tv_chat_log);
        setTv_chat_log(tv_chat_log);
        return rootView;
    }

    String getChildName() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_info",MODE_PRIVATE);
        String childName = preferences.getString("child_name", "");
        if (childName.length() < 3) {
            return childName;
        } else {
            return childName.substring(1);
        }
    }

    void setTv_chat_log(TextView tv) {
        tv.setText(getChildName() + "의\n하루");
    }
}