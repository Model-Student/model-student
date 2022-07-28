package com.tnedutsledom.modelstudent.house_work;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tnedutsledom.modelstudent.HouseWorkActivity;
import com.tnedutsledom.modelstudent.R;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {
    Context context = null;
    LayoutInflater layoutInflater = null;
    StaticElement se;
    public ArrayList<Work> workArrayList;
    public View house_work_item;
    Animation anim_alpha_100, anim_alpha_0;
    int nowCategory;

    public CustomAdaptor(Context context, int category) {
        se = new StaticElement();
        this.context = context;
        this.nowCategory = category;
        this.workArrayList = getProperList(category);
        layoutInflater = LayoutInflater.from(context);
        anim_alpha_100 = AnimationUtils.loadAnimation(context, R.anim.alpha100);
        anim_alpha_0 = AnimationUtils.loadAnimation(context, R.anim.alpha0);
    }

    public int getNowCategory() {
        return nowCategory;
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
                return se.workList;
            case 1:
                return se.house_work_list;
            case 2:
                return se.home_work_list;
            case 3:
                return se.eating_list;
            case 4:
                return se.etc_list;
            default:
                return null;
        }
    }

    void setOtherCategory(boolean selected, String category2, String name) {
        if (nowCategory == 0) {
            switch (category2) {
                case "집안일":
                    for (int j = 0; j < se.house_work_list.size(); j++) {
                        if (se.house_work_list.get(j).getWork_name().equals(name)) {
                            se.house_work_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "숙제":
                    for (int j = 0; j < se.home_work_list.size(); j++) {
                        if (se.home_work_list.get(j).getWork_name().equals(name)) {
                            se.home_work_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "음식":
                    for (int j = 0; j < se.eating_list.size(); j++) {
                        if (se.eating_list.get(j).getWork_name().equals(name)) {
                            se.eating_list.get(j).setSelected(selected);
                            break;
                        }
                    }
                case "기타":
                    for (int j = 0; j < se.etc_list.size(); j++) {
                        if (se.etc_list.get(j).getWork_name().equals(name)) {
                            se.etc_list.get(j).setSelected(selected);
                            break;
                        }
                    }
            }
        } else {
            for (int j = 0; j < se.workList.size(); j++) {
                if (se.workList.get(j).getWork_name().equals(name)) {
                    se.workList.get(j).setSelected(selected);
                    break;
                }
            }
        }
    }

    void deleteOtherCategory(String category2, String name) {
        if (nowCategory == 0) {
            switch (category2) {
                case "집안일":
                    for (int j = 0; j < se.house_work_list.size(); j++) {
                        Log.d("1123", "deleteOtherCategory: " + j);
                        if (se.house_work_list.get(j).getWork_name().equals(name)) {
                            Log.d("1111111111111111", "deleteOtherCategory: " + j);
                            se.house_work_list.remove(j);
                            break;
                        }
                    }
                case "숙제":
                    for (int j = 0; j < se.home_work_list.size(); j++) {
                        if (se.home_work_list.get(j).getWork_name().equals(name)) {
                            se.home_work_list.remove(j);
                            break;
                        }
                    }
                case "음식":
                    for (int j = 0; j < se.eating_list.size(); j++) {
                        if (se.eating_list.get(j).getWork_name().equals(name)) {
                            se.eating_list.remove(j);
                            break;
                        }
                    }
                case "기타":
                    for (int j = 0; j < se.etc_list.size(); j++) {
                        if (se.etc_list.get(j).getWork_name().equals(name)) {
                            se.etc_list.remove(j);
                            break;
                        }
                    }
            }
        } else {
            for (int j = 0; j < se.workList.size(); j++) {
                if (se.workList.get(j).getWork_name().equals(name)) {
                    se.workList.remove(j);
                    break;
                }
            }
        }
    }

    void deleteStrList(String name) {
        se.strList.remove(se.strList.indexOf(name));
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
        LinearLayout ll_item = house_work_item.findViewById(R.id.ll_hw_item);

        iv_selected.startAnimation(setVisible(i));

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (se.getDelete()) {
                    deleteOtherCategory(workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    deleteStrList(workArrayList.get(i).getWork_name());
                    workArrayList.remove(i);
                    HouseWorkActivity.updateListView(nowCategory);
                }
            }
        });

        iv_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_0);
                    workArrayList.get(i).setSelected(false);
                    setOtherCategory(false, workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                    HouseWorkActivity.updateListView(nowCategory);
                } else if (!workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_100);
                    workArrayList.get(i).setSelected(true);
                    setOtherCategory(true, workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                    HouseWorkActivity.updateListView(nowCategory);
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