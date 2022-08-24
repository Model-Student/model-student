package com.tnedutsledom.modelstudent.house_work;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tnedutsledom.modelstudent.R;

import java.util.ArrayList;
import java.util.List;

public class HouseWorkActivity extends AppCompatActivity {

    static Context context;         // 현재 '액티비티' 정보(권한)
    static StaticElement se;        // Static 요소 모음 클래스

    LinearLayout ll_btn_add;        // 할일 추가 버튼
    Dialog dl_add_work,             // 힐일 추가 팝업창
            dl_category;            // 카테고리 선택 팝업창
    ImageView iv_hw_delete,         // 할일 삭제 버튼
            iv_category;            // 카테고리 선택 버튼
    static TextView tv_item_count;  // 아이템 개수표시 뷰
    static ListView lv_work_list;   // 할일 리스트뷰

    int category;                   // 카테고리
                                    // 0 = 전체, 1 = 집안일, 2 = 숙제, 3 = 음식, 4 = 기타
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_work);
        init();                     // 변수, 객체 초기세팅
        setPopUpAddDialog();        // 할일 추가 버튼 세팅
        setToggleDelete();          // 할일 삭제 토글 버튼 세팅
        setCategoryDialog();        // 카테고리 버튼 세팅
    }

    // 선택된 할일목록을 반환해주는 메소드
    ArrayList getSelectedWorkList() {
        ArrayList<String> tmpList = new ArrayList<>();
        for (int i = 0; i < se.getWorkList().size(); i++) {
            if (se.getWorkList().get(i).getSelected()) {
                tmpList.add(se.getWorkList().get(i).getWork_name());
            }
        }
        return tmpList;
    }

    // 삭제버튼을 누를 때마다 delete 변수의 true|false 값을 바꾸도록 설정
    void setToggleDelete() {
        iv_hw_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!se.getDelete()) {
                    iv_hw_delete.setImageResource(R.drawable.ic_delete_fill);
                    se.setDelete(true);
                } else {
                    iv_hw_delete.setImageResource(R.drawable.ic_delete_not_fill);
                    se.setDelete(false);
                }
            }
        });
    }

    // iv_category를 터치했을 때 카테고리 팝업창이 뜨도록 설정
    void setCategoryDialog() {
        iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCategory();
            }
        });
    }

    // ll_btn_add를 터치했을 때 할일추가 팝업창이 뜨도록 설정
    void setPopUpAddDialog() {
        ll_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });
    }

    // 현재 화면에 표시되고있는 할일 개수 표시 0(선택된 할일) / 0(전체 할일)
    static void setItemCountView(WorkListViewAdaptor adaptor) {
        // 전체 할일 개수
        String count = String.valueOf(adaptor.getCount());
        // 선택된 할일 개수
        int checkCount = 0;
        ArrayList<Work> tmpList = getProperList(adaptor.getNowCategory());
        for (int i = 0; i < adaptor.getCount(); i++) {
            if (tmpList.get(i).getSelected()) {
                checkCount++;
            }
        }
        tv_item_count.setText(checkCount + " " + "/" + " " + count);
    }

    // category값에 따라서 해당하는 할일 리스트를 반환하는 메소드
    static ArrayList getProperList(int category) {
        switch (category) {
            case 0:
                return se.getWorkList();
            case 1:
                return se.getHouse_work_list();
            case 2:
                return se.getHome_work_list();
            case 3:
                return se.getEating_list();
            case 4:
                return se.getEtc_list();
            default:
                return null;
        }
    }

    // 전체 할일리스트에 할일을 추가해준다
    void addWorkList(String data, String category) {
        se.getWorkList().add(new Work(data, category));
        se.getStrList().add(data);
    }

    // 화면에 표시되는 리스트뷰의 상태를 새로고침 해준다
    public static void updateListView(int category) {
        final WorkListViewAdaptor adapter = new WorkListViewAdaptor(context, category);
        lv_work_list.setAdapter(adapter);
        lv_work_list.setSelection(adapter.getCount() - 1);
        setItemCountView(adapter);
    }

    // 할일추가버튼의 가로세로크기비율을 동일하게 설정
    void setAddBtnSize() {
        int view_x_size = ll_btn_add.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view_x_size, view_x_size);
        ll_btn_add.setLayoutParams(params);
    }

    // 받아온 카테고리에 해당하는 리스트에 할일을 추가해준다
    void addCategoryList(String data, String category) {
        switch (category) {
            case "집안일":
                se.getHouse_work_list().add(new Work(data, category));
                break;
            case "숙제":
                se.getHome_work_list().add(new Work(data, category));
                break;
            case "음식":
                se.getEating_list().add(new Work(data, category));
                break;
            case "기타":
                se.getEtc_list().add(new Work(data, category));
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAddBtnSize();
    }

    // 카테고리변경 팝업창을 표시하고 기능설정
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

    // 할일추가 팝업창을 표시하고 기능설정
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

                if (!se.getStrList().contains(value) && value.length() != 0) {
                    addWorkList(value, categoryTmp);
                    addCategoryList(value, categoryTmp);
                    updateListView(category);
                    dl_add_work.dismiss();

                } else if (se.getStrList().contains(value)) { // 할일이 중복되어있는지 확인
                    Toast.makeText(HouseWorkActivity.this, "이미 존재하는 할일입니다", Toast.LENGTH_SHORT).show();
                } else if (value.length() == 0) { // 입력값이 공백인지 확인
                    Toast.makeText(HouseWorkActivity.this, "할일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 객체, 변수 생성 및 뷰와 연결
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
        se = StaticElement.getInstance();
        iv_hw_delete = findViewById(R.id.iv_hw_delete);
        iv_category = findViewById(R.id.iv_hw_category);
        context = HouseWorkActivity.this;
    }
}