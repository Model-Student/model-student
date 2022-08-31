package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;

public class FragmentHelpDetail extends Fragment {

    View v;
    AppCompatButton btn_back;
    ImageView iv_ui;
    int category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help_detail, container, false);
        init();
        setBtn_back();
        setDataByCategory();

        return v;
    }


    public static FragmentHelpDetail newInstance() {
        return new FragmentHelpDetail();
    }

    void setDataByCategory() {
        switch (category) {
            case 0: iv_ui.setBackgroundColor(Color.parseColor("#3EA88F"));break;
            case 1: iv_ui.setBackgroundColor(Color.parseColor("#B65F78"));break;
            case 2: iv_ui.setBackgroundColor(Color.parseColor("#3B709A"));break;
            case 3: iv_ui.setBackgroundColor(Color.parseColor("#AA8B5C"));break;
        }
    }

    void setBtn_back() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentHelp.newInstance());
            }
        });
    }

    void init() {
        btn_back = v.findViewById(R.id.btn_help_detail_back);
        iv_ui = v.findViewById(R.id.iv_help_detail_ui);
        category = FragmentHelp.categoryHelp;
    }
}
