package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;

public class FragmentSettingNuguVoice extends Fragment {

    View v;
    TextView tv_title;
    AppCompatButton btn_confirm;

    public static FragmentSettingNuguVoice newInstance() {
        return new FragmentSettingNuguVoice();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting_nugu_voice, container, false);
        init();
        setBtn_confirm();
        return v;
    }

    void setBtn_confirm() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentSetting.newInstance());
            }
        });
    }

    public void setTvEmphasis(TextView tv) {
        String content = tv.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성

        String word = "목소리";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7C83FD")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    void init() {
        tv_title = v.findViewById(R.id.tv_setting_voice_title);
        setTvEmphasis(tv_title);
        btn_confirm = v.findViewById(R.id.btn_voice_confirm);
    }
}
