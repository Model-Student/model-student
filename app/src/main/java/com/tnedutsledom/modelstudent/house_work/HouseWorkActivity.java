package com.tnedutsledom.modelstudent.house_work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tnedutsledom.modelstudent.R;
import com.tnedutsledom.modelstudent.ThemeColorAdaptor;

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
    TextView tv_title;
    ThemeColorAdaptor colorAdaptor;

    int category;                   // 카테고리
    int a;
    // 0 = 전체, 1 = 집안일, 2 = 숙제, 3 = 음식, 4 = 기타
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    SharedPreferences SP;
    CollectionReference ColRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_work);
        init();                     // 변수, 객체 초기세팅
        getToFireBase();            // 파이어베이스에 있는 현재 값 가져오기
        setPopUpAddDialog();        // 할일 추가 버튼 세팅
        setToggleDelete();          // 할일 삭제 토글 버튼 세팅
        InitListViewForFireBase();  // 리스트뷰 데이터 초기 세팅
        setActivityTheme();
        setCategoryDialog();        // 카테고리 버튼 세팅

    }

    // 리스트뷰 데이터 초기 세팅
    void InitListViewForFireBase() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateListView(category);

            }
        },1000);
    }

    // 선택된 할일목록을 반환해주는 메소드
    ArrayList getWorkList() {
        ArrayList<Work> tmpList = new ArrayList<>();
        for (int i = 0; i < se.getWorkList().size(); i++) {
            tmpList.add(se.getWorkList().get(i));
        }
        return tmpList;
    }


    // 파이어베이스에 할일리스트를 업로드
    void sendToFireBase(ArrayList<Work> list){
        for (int i = 0; i < list.size(); i++) {
            Log.d("1","리스트 이름 "+ list.get(i));
            firebaseFirestore.
                    collection("model_student").
                    document(SP.getString("email","")).
                    collection("HouseWork").document("item" + i).set(new FirebaseAdaptor(
                            list.get(i).getWork_name(),
                            list.get(i).getCategory(),
                            list.get(i).getSelected(),
                            list.get(i).getMemo()
                    ));
        }
    }

    // 파이어베이스에 할일리스트를 받아오기
    void getToFireBase(){
        ColRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                addWorkList(document.getString("work_name"),
                                        document.getString("category"),
                                        document.getBoolean("selected"),
                                        document.getString("memo"));
                                addCategoryList(document.getString("work_name"),
                                        document.getString("category"),
                                        document.getBoolean("selected"),
                                        document.getString("memo"));
                            }
                        } else {
                            //실패했을 경우
                        }
                    }
                });
    }


    void deleteInternalList() {
        se.getStrList().clear();
        se.getWorkList().clear();
        se.getHouse_work_list().clear();
        se.getHome_work_list().clear();
        se.getEating_list().clear();
        se.getEtc_list().clear();
    }

    // 파이어베이스에서 삭제
    void deleteDataInFireBase(){
        ColRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            a = task.getResult().size();
                        } else {
                            //실패했을 경우
                        }
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= a; i++) {
                    firebaseFirestore.
                            collection("model_student").
                            document(SP.getString("email","")).
                            collection("HouseWork")
                            .document("item" + i).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("하우스 워크 아이템 삭제", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("하우스 워크 아이템 삭제", "Error deleting document", e);
                                }
                            });
                }
            }
        }, 500);

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
    void addWorkList(String data, String category, boolean selected, String memo) {
        se.getWorkList().add(new Work(data, category, selected, memo));
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
    void addCategoryList(String data, String category, boolean selected, String memo) {
        switch (category) {
            case "집안일":
                se.getHouse_work_list().add(new Work(data, category, selected, memo));
                break;
            case "숙제":
                se.getHome_work_list().add(new Work(data, category, selected, memo));
                break;
            case "음식":
                se.getEating_list().add(new Work(data, category, selected, memo));
                break;
            case "기타":
                se.getEtc_list().add(new Work(data, category, selected, memo));
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

        ImageView[] lv_category = new ImageView[5];
        lv_category[0] = dl_category.findViewById(R.id.iv_hw_category_all);
        lv_category[1] = dl_category.findViewById(R.id.iv_hw_category_house_work);
        lv_category[2] = dl_category.findViewById(R.id.iv_hw_category_home_work);
        lv_category[3] = dl_category.findViewById(R.id.iv_hw_category_eat);
        lv_category[4] = dl_category.findViewById(R.id.iv_hw_category_etc);

        TextView[] tv_category = new TextView[5];
        tv_category[0] = dl_category.findViewById(R.id.tv_hw_category_all);
        tv_category[1] = dl_category.findViewById(R.id.tv_hw_category_house_work);
        tv_category[2] = dl_category.findViewById(R.id.tv_hw_category_home_work);
        tv_category[3] = dl_category.findViewById(R.id.tv_hw_category_eat);
        tv_category[4] = dl_category.findViewById(R.id.tv_hw_category_etc);

        ConstraintLayout cl_top_bar = dl_category.findViewById(R.id.cl_hw_dia_top_bar_cate);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dialog_hw_top_background);
        colorAdaptor.setViewColorText(drawable, cl_top_bar);

        for (int i = 0; i < 5; i++) {
            colorAdaptor.setViewColorTheme(tv_category[i]);
            colorAdaptor.setViewColorTheme(lv_category[i]);
        }

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
        EditText et_memo = dl_add_work.findViewById(R.id.et_hw_memo);
        et_memo.setText("");
        et_value.setText("");
        et_value.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        Spinner spinner = dl_add_work.findViewById(R.id.spinner_hw_category);
        SpinnerAdaptor spinnerAdaptor = new SpinnerAdaptor(getApplicationContext(), category_list);
        spinner.setAdapter(spinnerAdaptor);

        ImageView iv_dialog_close, iv_dialog_check;
        iv_dialog_check = dl_add_work.findViewById(R.id.iv_btn_dl_check);
        iv_dialog_close = dl_add_work.findViewById(R.id.iv_btn_dl_close);

        ConstraintLayout cl_top_bar;
        cl_top_bar = dl_add_work.findViewById(R.id.cl_hw_dia_top_bar);

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.dialog_hw_top_background);
        colorAdaptor.setViewColorText(drawable, cl_top_bar);
        colorAdaptor.setViewColorText(iv_dialog_check);


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
                String memoTmp = et_memo.getText().toString();

                if (!se.getStrList().contains(value) && value.length() != 0) {
                    addWorkList(value, categoryTmp,true, memoTmp);
                    addCategoryList(value, categoryTmp, true, memoTmp);
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
        SP = getSharedPreferences("user_info",MODE_PRIVATE);
        ColRef = firebaseFirestore.collection("model_student").
                document(SP.getString("email","")).
                collection("HouseWork");
        tv_title = findViewById(R.id.tv_hw_title);
        colorAdaptor = ThemeColorAdaptor.getInstance(getApplicationContext());
    }

    void setActivityTheme() {
        colorAdaptor.setTheme(SP.getInt("theme", 0));
        colorAdaptor.setViewColorTheme(tv_title);
        colorAdaptor.setViewColorTheme(iv_category);
        colorAdaptor.setViewColorTheme(iv_hw_delete);
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.house_work_btn_add_background);
        colorAdaptor.setViewColorBtn(drawable, ll_btn_add);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Log.d("전체 리스트", "onPause: " + se.getWorkList().size());
            deleteDataInFireBase();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendToFireBase(getWorkList());
                    deleteInternalList();
                }
            }, 900);


        } catch (Exception e) {

        }
    }
}