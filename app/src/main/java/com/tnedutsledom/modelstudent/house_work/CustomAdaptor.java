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
    public ArrayList<Work> workArrayList;
    public View house_work_item;
    Animation anim_alpha_100, anim_alpha_0;

    public CustomAdaptor(Context context, ArrayList<Work> data) {
        this.context = context;
        this.workArrayList = data;
        layoutInflater = LayoutInflater.from(context);
        anim_alpha_100 = AnimationUtils.loadAnimation(context, R.anim.alpha100);
        anim_alpha_0 = AnimationUtils.loadAnimation(context, R.anim.alpha0);
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

        iv_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_0);
                    workArrayList.get(i).setSelected(false);
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                } else if (!workArrayList.get(i).getSelected()) {
                    iv_selected.startAnimation(anim_alpha_100);
                    workArrayList.get(i).setSelected(true);
                    Log.d("sdsadda", "onClick: " + workArrayList.get(i).getSelected());
                }
            }
        });

        tv_work_name.setText(workArrayList.get(i).getWork_name());
        switch (workArrayList.get(i).getCategory()){
            case "집안일" : iv_category.setColorFilter(ContextCompat.getColor(context,R.color.hw_house_work));break;
            case "숙제" : iv_category.setColorFilter(ContextCompat.getColor(context,R.color.hw_home_work));break;
            case "음식" : iv_category.setColorFilter(ContextCompat.getColor(context,R.color.hw_eating));break;
            case "기타" : iv_category.setColorFilter(ContextCompat.getColor(context,R.color.hw_etc));break;
            default: iv_category.setColorFilter(ContextCompat.getColor(context,R.color.hw_home_work));break;
        }

        return house_work_item;
    }

}