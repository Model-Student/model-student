package com.tnedutsledom.modelstudent.house_work;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.rpc.context.AttributeContext;
import com.tnedutsledom.modelstudent.R;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdaptor extends BaseAdapter {
    Context context = null;
    LayoutInflater layoutInflater = null;
    WorkList wl;
    public ArrayList<Work> workArrayList;
    public View house_work_item;
    Animation anim_alpha_100, anim_alpha_0;
    int nowCategory;

    public CustomAdaptor(Context context, int category) {
        wl = new WorkList();
        this.context = context;
        this.nowCategory = category;
        this.workArrayList = getProperList(category);
        layoutInflater = LayoutInflater.from(context);
        anim_alpha_100 = AnimationUtils.loadAnimation(context, R.anim.alpha100);
        anim_alpha_0 = AnimationUtils.loadAnimation(context, R.anim.alpha0);
    }

    Animation setVisible(int i) {
        if (!workArrayList.get(i).getSelected()) {
            return anim_alpha_0;
        } else {
            return anim_alpha_100;
        }
    }

    ArrayList getProperList(int category) {
        switch (category) {
            case 0:
                return wl.workList;
            case 1:
                return wl.house_work_list;
            case 2:
                return wl.home_work_list;
            case 3:
                return wl.eating_list;
            case 4:
                return wl.etc_list;
            default:
                return null;
        }
    }

    void setOtherCategory(boolean selected, String category2, String name) {
        if (nowCategory == 0) {
            switch (category2) {
                case "집안일":
                    for (int j = 0; j < wl.house_work_list.size(); j++) {
                        if (wl.house_work_list.get(j).getWork_name().equals(name)) {
                            wl.house_work_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "숙제":
                    for (int j = 0; j < wl.home_work_list.size(); j++) {
                        if (wl.home_work_list.get(j).getWork_name().equals(name)) {
                            wl.home_work_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "음식":
                    for (int j = 0; j < wl.eating_list.size(); j++) {
                        if (wl.eating_list.get(j).getWork_name().equals(name)) {
                            wl.eating_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "기타":
                    for (int j = 0; j < wl.etc_list.size(); j++) {
                        if (wl.etc_list.get(j).getWork_name().equals(name)) {
                            wl.etc_list.get(j).setSelected(selected);
                            break;
                        }
                    }
            }
        } else {
            for (int j = 0; j < wl.workList.size(); j++) {
                if (wl.workList.get(j).getWork_name().equals(name)) {
                    wl.workList.get(j).setSelected(selected);
                    break;
                }
            }
        }
    }

    @Override
    public int getCount() {
        return workArrayList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return workArrayList.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        house_work_item = layoutInflater.inflate(R.layout.house_work_item, null);
        ImageView iv_category = house_work_item.findViewById(R.id.iv_hw_category);
        TextView tv_work_name = house_work_item.findViewById(R.id.tv_hw_work_name);
        ImageView iv_selected = house_work_item.findViewById(R.id.iv_hw_selected);

        iv_selected.startAnimation(setVisible(i));

        iv_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_0);
                    workArrayList.get(i).setSelected(false);
                    setOtherCategory(false, workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                } else if (!workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_100);
                    workArrayList.get(i).setSelected(true);
                    setOtherCategory(true, workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                }
            }
        });

        tv_work_name.setText(workArrayList.get(i).getWork_name());
        switch (workArrayList.get(i).getCategory()) {
            case "집안일":
                iv_category.setColorFilter(ContextCompat.getColor(context, R.color.hw_house_work));
                break;
            case "숙제":
                iv_category.setColorFilter(ContextCompat.getColor(context, R.color.hw_home_work));
                break;
            case "음식":
                iv_category.setColorFilter(ContextCompat.getColor(context, R.color.hw_eating));
                break;
            case "기타":
                iv_category.setColorFilter(ContextCompat.getColor(context, R.color.hw_etc));
                break;
            default:
                iv_category.setColorFilter(ContextCompat.getColor(context, R.color.hw_home_work));
                break;
        }

        return house_work_item;
    }

}