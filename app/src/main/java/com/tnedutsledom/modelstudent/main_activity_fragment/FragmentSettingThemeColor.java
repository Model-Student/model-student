package com.tnedutsledom.modelstudent.main_activity_fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;

public class FragmentSettingThemeColor extends Fragment {

    View v;
    TextView tv_title;
    AppCompatButton btn_confirm;
    ThemeColorAdaptor colorAdaptor;
    ImageView[] iv_theme;
    int theme = 0;


    public static FragmentSettingThemeColor newInstance() {
        return new FragmentSettingThemeColor();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting_theme_color, container, false);
        init();
        setSelectTheme();
        return v;
    }

    public void setSelectTheme() {
        for (int i = 0; i < iv_theme.length; i++) {
            final int tmp = i;
            iv_theme[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theme = tmp;
                    colorAdaptor.setTheme(theme);
                    SharedPreferences preferences = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("theme", theme);
                    editor.commit();
                    ((MainActivity) getActivity()).setActivityTheme();
                    ((MainActivity) getActivity()).replaceFragment(FragmentSetting.newInstance());
                }
            });
        }
    }

    public void setTvEmphasis(TextView tv) {
        String content = tv.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성

        String word = "색상";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#7C83FD")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    void init() {
        tv_title = v.findViewById(R.id.tv_setting_theme_color_title);
        setTvEmphasis(tv_title);
        colorAdaptor = ThemeColorAdaptor.getInstance(getActivity().getApplicationContext());
        iv_theme = new ImageView[8];
        iv_theme[0] = v.findViewById(R.id.iv_theme_0);
        iv_theme[1] = v.findViewById(R.id.iv_theme_1);
        iv_theme[2] = v.findViewById(R.id.iv_theme_2);
        iv_theme[3] = v.findViewById(R.id.iv_theme_3);
        iv_theme[4] = v.findViewById(R.id.iv_theme_4);
        iv_theme[5] = v.findViewById(R.id.iv_theme_5);
        iv_theme[6] = v.findViewById(R.id.iv_theme_6);
        iv_theme[7] = v.findViewById(R.id.iv_theme_7);
    }

}
