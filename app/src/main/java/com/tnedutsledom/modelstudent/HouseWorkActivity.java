package com.tnedutsledom.modelstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tnedutsledom.modelstudent.house_work.CustomAdaptor;
import com.tnedutsledom.modelstudent.house_work.SpinnerAdaptor;
import com.tnedutsledom.modelstudent.house_work.Work;

import java.util.ArrayList;
import java.util.List;

public class HouseWorkActivity extends AppCompatActivity {

    LinearLayout ll_btn_add;
    TextView tv_item_count;
    ListView lv_work_list;
    ArrayList<Work> workList;
    Dialog dl_add_work;
    ImageView iv_hw_delete;
    boolean delete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_work);
        init();
        popUpAddDialog();
        setToggleDelete();
        setListTouch();
    }

    void setToggleDelete() {
        iv_hw_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!delete) {
                    iv_hw_delete.setImageResource(R.drawable.ic_delete_fill);
                    delete = true;
                } else {
                    iv_hw_delete.setImageResource(R.drawable.ic_delete_not_fill);
                    delete = false;
                }
            }
        });
    }

    void setListTouch() {
        lv_work_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (delete) {
                    workList.remove(i);
                    updateListView();
                }
            }
        });
    }

    void popUpAddDialog() {
        ll_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    void setItemCountView(CustomAdaptor adaptor) {
        String count = String.valueOf(adaptor.getCount());
        tv_item_count.setText(count);
    }

    void addWorkList(String data, String category) {
        Log.d("데이터 추가", "addWorkList: " + data + category);
        workList.add(new Work(data, category));
    }

    void updateListView() {
        final CustomAdaptor adapter = new CustomAdaptor(this,workList);
        lv_work_list.setAdapter(adapter);
        lv_work_list.setSelection(adapter.getCount()-1);
        setItemCountView(adapter);
    }

    void setAddBtnSize() {
        int view_x_size = ll_btn_add.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view_x_size, view_x_size);
        ll_btn_add.setLayoutParams(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAddBtnSize();
    }

    void showDialog() {
        dl_add_work.show();
        dl_add_work.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        List<String> category_list = new ArrayList<>();
        category_list.add("집안일");
        category_list.add("숙제");
        category_list.add("음식");
        category_list.add("기타");
        EditText et_value = dl_add_work.findViewById(R.id.et_hw_text);
        Spinner spinner = dl_add_work.findViewById(R.id.spinner_hw_category);
        TextView tv = findViewById(R.id.spinner_hw_outer_text);
        SpinnerAdaptor spinnerAdaptor = new SpinnerAdaptor(getApplicationContext(), category_list);
        spinner.setAdapter(spinnerAdaptor);
        ImageView iv_dialog_close, iv_dialog_check;
        iv_dialog_check = dl_add_work.findViewById(R.id.iv_btn_dl_check);
        iv_dialog_close = dl_add_work.findViewById(R.id.iv_btn_dl_close);

        iv_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_add_work.dismiss();
            }
        });

        iv_dialog_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkList(et_value.getText().toString(), spinner.getSelectedItem().toString());
                updateListView();
                dl_add_work.dismiss();
            }
        });

    }

    void init() {
        ll_btn_add = findViewById(R.id.ll_house_work_add);
        lv_work_list = findViewById(R.id.recycleV_hw_work_list);
        tv_item_count = findViewById(R.id.tv_item_count);
        dl_add_work = new Dialog(HouseWorkActivity.this);
        dl_add_work.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl_add_work.setContentView(R.layout.dialog_add_house_work);
        workList = new ArrayList<>();
        iv_hw_delete = findViewById(R.id.iv_hw_delete);
    }
}