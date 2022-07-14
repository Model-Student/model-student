package com.tnedutsledom.modelstudent.house_work;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    ArrayList<Work> workArrayList;

    public CustomAdaptor(Context context, ArrayList<Work> data) {
        this.context = context;
        this.workArrayList = data;
        layoutInflater = LayoutInflater.from(context);
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
        View house_work_item = layoutInflater.inflate(R.layout.house_work_item, null);
        TextView tv_work_name = house_work_item.findViewById(R.id.tv_hw_work_name);
        ImageView iv_category = house_work_item.findViewById(R.id.iv_hw_category);

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