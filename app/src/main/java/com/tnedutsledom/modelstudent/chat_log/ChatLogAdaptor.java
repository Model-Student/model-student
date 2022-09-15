package com.tnedutsledom.modelstudent.chat_log;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tnedutsledom.modelstudent.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ChatLogAdaptor extends BaseAdapter {

    Context context = null;                 // 현재 '액티비티' 정보(권한)
    LayoutInflater layoutInflater = null;   // 미리 xml로 만들어둔 아이템을 메모리상으로 올려주는 역할
    View item_date, item_nugu, item_kid;    // 각각 날짜표시 xml, 누구채팅 xml, 아이채팅 xml

    ArrayList<ChatLogItem> chat_log_list;   // 채팅 아이템 리스트

    public ChatLogAdaptor(Context context, ArrayList chat_log_list) {
        this.context = context;
        this.chat_log_list = chat_log_list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chat_log_list.size();
    }

    @Override
    public Object getItem(int i) {
        return chat_log_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 첫번째 아이템일 경우 최상단에 오늘 날짜를 표시
        if (i == 0) {
            item_date = layoutInflater.inflate(R.layout.chat_log_date, null);
            TextView tv_date = item_date.findViewById(R.id.tv_cl_date);
            tv_date.setText(getDate());
            return item_date;
        }
        // 채팅 아이템의 종류가 'nugu'일 경우 해당하는 view로 데이터를 적용하여 반환
        else if (Objects.equals(chat_log_list.get(i).getNugu_or_kid(), "nugu")) {
            item_nugu = layoutInflater.inflate(R.layout.chat_log_nugu, null);
            TextView tv_value = item_nugu.findViewById(R.id.tv_cl_nugu_text);
            ImageView iv_back = item_nugu.findViewById(R.id.iv_cl_nugu_text_back);
            TextView tv_time = item_nugu.findViewById(R.id.tv_cl_nugu_time);

            tv_time.setText(chat_log_list.get(i).getTime());
            tv_value.setText(chat_log_list.get(i).getChat_data());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewSize(tv_value, iv_back);

                }
            }, 100);

            return item_nugu;
        }
        //채팅 아이템의 종류가 'kid'일 경우 해당하는 view로 데이터를 적용하여 반환
        else {
            item_kid = layoutInflater.inflate(R.layout.chat_log_kid, null);
            TextView tv_value = item_kid.findViewById(R.id.tv_cl_kid_text);
            ImageView iv_back = item_kid.findViewById(R.id.iv_cl_kid_text_back);
            TextView tv_time = item_kid.findViewById(R.id.tv_cl_kid_time);

            tv_time.setText(chat_log_list.get(i).getTime());
            tv_value.setText(chat_log_list.get(i).getChat_data());
            setViewSize(tv_value, iv_back);
            return item_kid;
        }
    }

    // 채팅 배경이미지의 크기를 텍스트 길이에 맞도록 크기지정
    void setViewSize(TextView tv_value, ImageView iv_back) {
        Rect realSize = new Rect();
        tv_value.getPaint().getTextBounds(tv_value.getText().toString(), 0, tv_value.getText().length(), realSize);
        iv_back.getLayoutParams().width = realSize.width() + 100;
        Log.d("가로길이", "setViewSize: " + tv_value.getWidth());
    }

    // 오늘 날짜를 반환
    String getDate() {
        long today = System.currentTimeMillis();
        Date date = new Date(today);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy MM dd");
        String getTime = simpleDate.format(date);
        return getTime;
    }
}