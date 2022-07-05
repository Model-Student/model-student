package com.tnedutsledom.modelstudent.carouselitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.LastTimeActivity;
import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;

import java.time.Instant;


public class FragmentLastTime extends Fragment {

    LinearLayout btn_last_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_lasttime, container, false);

        btn_last_time = rootView.findViewById(R.id.btn_main_last_time);
        btn_last_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LastTimeActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
