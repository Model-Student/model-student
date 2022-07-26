package com.tnedutsledom.modelstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tnedutsledom.modelstudent.house_work.CustomAdaptor;
import com.tnedutsledom.modelstudent.house_work.SpinnerAdaptor;
import com.tnedutsledom.modelstudent.house_work.Work;
import com.tnedutsledom.modelstudent.house_work.WorkList;

import java.util.ArrayList;
import java.util.List;

public class HouseWorkActivity extends AppCompatActivity {

    LinearLayout ll_btn_add;
    TextView tv_item_count;
    ListView lv_work_list;
    WorkList wl;
    Dialog dl_add_work, dl_category;
    ImageView iv_hw_delete, iv_category;
    boolean delete = false;
    int category = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_work);
        init();
        popUpAddDialog();
        setToggleDelete();
        setListTouch();
        setCategoryDialog();
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

    void setCategoryDialog() {
        iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCategory();
            }
        });
    }

    void setListTouch() {
        lv_work_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (delete) {
                    wl.workList.remove(i);
                    updateListView(category);
                }
            }
        });
    }

    void popUpAddDialog() {
        ll_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
    }

    void setItemCountView(CustomAdaptor adaptor) {
        String count = String.valueOf(adaptor.getCount());
        tv_item_count.setText(count);
    }

    void addWorkList(String data, String category) {
        wl.workList.add(new Work(data, category));
        wl.strList.add(data);
    }

    void updateListView(int category) {
        final CustomAdaptor adapter = new CustomAdaptor(this, category);
        lv_work_list.setAdapter(adapter);
        lv_work_list.setSelection(adapter.getCount() - 1);
        setItemCountView(adapter);
    }

    void setAddBtnSize() {
        int view_x_size = ll_btn_add.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view_x_size, view_x_size);
        ll_btn_add.setLayoutParams(params);
    }

    void addCategoryList(String data, String category) {
        switch (category) {
            case "집안일":
                wl.house_work_list.add(new Work(data, category));
                break;
            case "숙제":
                wl.home_work_list.add(new Work(data, category));
                break;
            case "음식":
                wl.eating_list.add(new Work(data, category));
                break;
            case "기타":
                wl.etc_list.add(new Work(data, category));
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAddBtnSize();
    }

    void showDialogCategory() {
        dl_category.show();
        dl_category.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout[] ll_btn_category = new LinearLayout[5];
        ll_btn_category[0] = dl_category.findViewById(R.id.ll_hw_category_all);
        ll_btn_category[1] = dl_category.findViewById(R.id.ll_hw_category_house_work);
        ll_btn_category[2] = dl_category.findViewById(R.id.ll_hw_category_home_work);
        ll_btn_category[3] = dl_category.findViewById(R.id.ll_hw_category_eat);
        ll_btn_category[4] = dl_category.findViewById(R.id.ll_hw_category_etc);

        for (int i = 0; i < ll_btn_category.length; i++) {
            int finalI = i;
            ll_btn_category[finalI].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.ll_hw_category_all:
                            category = 0;
                            break;
                        case R.id.ll_hw_category_house_work:
                            category = 1;
                            break;
                        case R.id.ll_hw_category_home_work:
                            category = 2;
                            break;
                        case R.id.ll_hw_category_eat:
                            category = 3;
                            break;
                        case R.id.ll_hw_category_etc:
                            category = 4;
                            break;
                    }
                    updateListView(category);
                    dl_category.dismiss();
                }
            });
        }

    }

    void showDialogAdd() {
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
                String value = et_value.getText().toString();
                String categoryTmp = spinner.getSelectedItem().toString();

                if (!wl.strList.contains(value) && value.length() != 0) {
                    addWorkList(value, categoryTmp);
                    addCategoryList(value, categoryTmp);
                    updateListView(category);
                    dl_add_work.dismiss();

                } else if(wl.strList.contains(value)){
                    Toast.makeText(HouseWorkActivity.this, "이미 존재하는 할일입니다", Toast.LENGTH_SHORT).show();

                } else if (value.length() == 0) {
                    Toast.makeText(HouseWorkActivity.this, "할일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void init() {
        ll_btn_add = findViewById(R.id.ll_house_work_add);
        lv_work_list = findViewById(R.id.recycleV_hw_work_list);
        tv_item_count = findViewById(R.id.tv_item_count);
        dl_add_work = new Dialog(HouseWorkActivity.this);
        dl_add_work.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl_add_work.setContentView(R.layout.dialog_hw_add);
        dl_category = new Dialog(HouseWorkActivity.this);
        dl_category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dl_category.setContentView(R.layout.dialog_hw_category);
        wl.workList = new ArrayList<>();
        wl.house_work_list = new ArrayList<>();
        wl.home_work_list = new ArrayList<>();
        wl.eating_list = new ArrayList<>();
        wl.etc_list = new ArrayList<>();
        wl.strList = new ArrayList<>();
        iv_hw_delete = findViewById(R.id.iv_hw_delete);
        iv_category = findViewById(R.id.iv_hw_category);
    }
}