package com.tnedutsledom.modelstudent.house_work;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tnedutsledom.modelstudent.R;

import java.util.List;

public class SpinnerAdaptor extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater = null;
    private String text;

    public SpinnerAdaptor(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    // 화면에 들어왔을 때 보여지는 텍스트뷰 설정
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item_view = inflater.inflate(R.layout.spinner_hw_outer_item, null);
        TextView tv_spinner_outer = item_view.findViewById(R.id.spinner_hw_outer_text);
        if (list != null) {
            text = list.get(position);
            tv_spinner_outer.setText(text);
        }
        return item_view;
    }

    // 클릭 후 나타나는 텍스트뷰 설정
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.spinner_hw_inner_item, parent, false);

        TextView tv_spinner_inner = convertView.findViewById(R.id.spinner_hw_inner_text);
        if (list != null) {
            text = list.get(position);
            tv_spinner_inner.setText(text);
        }

        return convertView;
    }
}
