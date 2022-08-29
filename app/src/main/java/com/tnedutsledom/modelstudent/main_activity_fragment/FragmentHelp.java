package com.tnedutsledom.modelstudent.main_activity_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tnedutsledom.modelstudent.R;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.Menu;
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor;

public class FragmentHelp extends Fragment {

    View v;
    ExpandableBottomBar bottom_bar;
    Menu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_help, container, false);
        init();
        initBottomBar();

        return v;
    }

    void initBottomBar() {
//        menu.add(
//                new MenuItemDescriptor.Builder(getActivity(), R.id.icon_todo, R.drawable.ic_todo, R.string.text, Color.GRAY).build()
//        );
//
//        menu.add(
//                new MenuItemDescriptor.Builder(getActivity(), R.id.icon_chat, R.drawable.ic_chat, R.string.text2, 0xffff77a9).build()
//        );
//
//        menu.add(
//                new MenuItemDescriptor.Builder(getActivity(), R.id.icon_moon, R.drawable.ic_moon, R.string.text3, 0xff58a5f0).build()
//        );
//
//        menu.add(
//                new MenuItemDescriptor.Builder(getActivity(), R.id.icon_settings, R.drawable.ic_settings_24, R.string.text4, 0xffbe9c91).build()
//        );

        bottom_bar.setOnItemSelectedListener((view, item, byUser) -> {
            Log.d("도움말 메뉴 선택", "selected: " + item.toString());
            return null;
        });

        bottom_bar.setOnItemReselectedListener((view, item, byUser) -> {
            Log.d("도움말 메뉴 선택 해제", "reselected: " + item.toString());
            return null;
        });
    }

    void init() {
        bottom_bar = v.findViewById(R.id.expandable_bottom_bar);
        menu = bottom_bar.getMenu();
    }
}
