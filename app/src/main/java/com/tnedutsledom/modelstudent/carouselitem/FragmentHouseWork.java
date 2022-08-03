package com.tnedutsledom.modelstudent.carouselitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.house_work.HouseWorkActivity;
import com.tnedutsledom.modelstudent.R;


public class FragmentHouseWork extends Fragment {

    LinearLayout ll_house_work;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_housework, container, false);
        ll_house_work = rootView.findViewById(R.id.btn_main_house_work);
        ll_house_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HouseWorkActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
