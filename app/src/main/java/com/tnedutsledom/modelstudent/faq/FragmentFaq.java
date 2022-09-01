package com.tnedutsledom.modelstudent.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andexert.expandablelayout.library.ExpandableLayoutListView;
import com.tnedutsledom.modelstudent.R;

public class FragmentFaq extends Fragment {

    View v;
    CustomExpandableLayoutListView lv_faq;
    ArrayAdapter arrayAdapter;
    String[] answer, question;

    public static FragmentFaq newInstance() {
        return new FragmentFaq();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_faq, container, false);
        init();
        setLv_faq();
        return v;
    }

    void setLv_faq() {
        arrayAdapter = new ArrayAdapter(getActivity(), R.layout.faq_list_item, R.id.header_text, question);
        lv_faq.setArray_answer(answer);
        lv_faq.setAdapter(arrayAdapter);
    }

    void init() {
        lv_faq = v.findViewById(R.id.lv_faq);
        question = getResources().getStringArray(R.array.faq_question);
        answer = getResources().getStringArray(R.array.faq_answer);
    }

}
