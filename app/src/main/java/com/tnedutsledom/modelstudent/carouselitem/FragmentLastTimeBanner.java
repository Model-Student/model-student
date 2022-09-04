package com.tnedutsledom.modelstudent.carouselitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.FragmentLastTime;
import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.chat_log.ChatLogFragment;


public class FragmentLastTimeBanner extends Fragment {

    LinearLayout btn_last_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_last_time_banner, container, false);

        btn_last_time = rootView.findViewById(R.id.btn_main_last_time);
        btn_last_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentLastTime.newInstance());
            }
        });

        return rootView;
    }
}
