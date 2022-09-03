package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.MainActivity;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.Menu;
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor;

public class FragmentHelp extends Fragment {

    View v;
    ExpandableBottomBar bottom_bar;
    Menu menu;
    TextView tv_title, tv_description;
    ImageView iv_banner, iv_back;
    LinearLayout ll_back;
    ThemeColorAdaptor colorAdaptor;
    TextView tv_start_detail;
    public static int categoryHelp = 0;
    String title;
    String[] titleColor = {"#3EA88F","#B65F78","#3B709A","#AA8B5C"};
    String[] textValue = {
            "양치, 이불정리, 손씻기 등등\n아이가 해야할 행동을 간편하게!\n\n앱에 할일을 등록해두면\n아이가 언제든 할일을 확인가능합니다.",
            "아이가 집에서 언제 무엇을 했는지\n누구와의 기록을 확인해보세요!\n\n아이의 일과를 언제나 한눈에!\n *아이가 주도적으로 일과를 정하도록 응원해주세요..!",
            "아이가 요즘 어떤하루를 보내는지\n아이가 느낀 하루가 궁금하다면?\n\n캘린더를 통해 원하는 날짜의\n아이의 기분을 알 수 있습니다.",
            "부모/자녀 이름 변경, 테마색 변경, nugu목소리 설정 등등\n모델스튜던트의 기능설정입니다."
    };
    int[] banner = {R.drawable.btn_image_house_work, R.drawable.banner_chat_log, R.drawable.btn_image_last_time, R.drawable.banner_settings};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help, container, false);
        init();
        initBottomBar();
        settv_start_detail();
        setThemeColor();

        return v;
    }

    public static FragmentHelp newInstance() {
        return new FragmentHelp();
    }

    void settv_start_detail() {
        tv_start_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(FragmentHelpDetail.newInstance());
            }
        });
    }

    void initBottomBar() {

        bottom_bar.setOnItemSelectedListener((view, item, byUser) -> {
            Log.d("도움말 메뉴 선택", "selected: " + item.getText());
            title = item.getText().toString();
            categoryHelp = getCategory(title);
            setHelpView(categoryHelp, title);
            return null;
        });

        bottom_bar.setOnItemReselectedListener((view, item, byUser) -> {
            Log.d("도움말 메뉴 선택 해제", "reselected: " + item.getText().toString());
            return null;
        });
    }

    void setHelpView(int category, String title) {
        tv_title.setText(title);
        tv_title.setTextColor(Color.parseColor(titleColor[category]));
        tv_description.setText(textValue[category]);
        iv_banner.setImageResource(banner[category]);
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryHelp = 0;
        setHelpView(categoryHelp, "하우스워크");
        setThemeColor();
    }

    void setThemeColor() {
        colorAdaptor.setViewColorBack(ll_back);
        colorAdaptor.setViewColorImg(iv_back);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.help_btn_shape);
        colorAdaptor.setViewColorText(drawable, tv_start_detail);
    }

    int getCategory(String value) {
        switch (value) {
            case "하우스워크": return 0;
            case "아이의 하루": return 1;
            case "라스트타임": return 2;
            case "설정": return 3;
            default:  return 0;
        }
    }

    void init() {
        bottom_bar = v.findViewById(R.id.expandable_bottom_bar);
        tv_title = v.findViewById(R.id.tv_help_title);
        tv_description = v.findViewById(R.id.tv_help_description);
        iv_banner = v.findViewById(R.id.iv_help_banner);
        tv_start_detail = v.findViewById(R.id.btn_help_start_detail);
        menu = bottom_bar.getMenu();
        setHelpView(categoryHelp, "하우스워크");
        colorAdaptor = ThemeColorAdaptor.getInstance(getActivity().getApplicationContext());
        ll_back = v.findViewById(R.id.ll_help_back);
        iv_back = v.findViewById(R.id.iv_help_back);
    }
}
