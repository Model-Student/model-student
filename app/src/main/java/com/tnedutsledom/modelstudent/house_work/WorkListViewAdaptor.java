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

import com.tnedutsledom.modelstudent.R;

import java.util.ArrayList;

public class WorkListViewAdaptor extends BaseAdapter {

    Context context = null;                 // 현재 '액티비티' 정보(권한)
    LayoutInflater layoutInflater = null;   // 미리 xml로 만들어둔 아이템을 메모리상으로 올려주는 역할

    StaticElement se;                       // Static 요소 모음 클래스

    public ArrayList<Work> workArrayList;   // 현재 화면에 표시되는 할일 리스트
    public View house_work_item;            // 리스트뷰에 들어갈 아이템
    Animation anim_alpha_100, anim_alpha_0; // 각각 투명도 100, 0

    int nowCategory;                        // 현재 화면에 표시되는 카테고리

    public WorkListViewAdaptor(Context context, int category) {
        se = StaticElement.getInstance();
        this.context = context;
        this.nowCategory = category;
        this.workArrayList = getProperList(category);
        layoutInflater = LayoutInflater.from(context);
        anim_alpha_100 = AnimationUtils.loadAnimation(context, R.anim.alpha100);
        anim_alpha_0 = AnimationUtils.loadAnimation(context, R.anim.alpha0);
    }

    // 현재 화면에 표시되고있는 카테고리를 반환
    public int getNowCategory() {
        return nowCategory;
    }

    // 아이템이 선택되어있는지 아닌지를 판단하여 표시상태를 변경
    Animation setVisible(int i) {
        if (!workArrayList.get(i).getSelected()) {
            return anim_alpha_0;
        } else {
            return anim_alpha_100;
        }
    }

    // category값에 따라서 해당하는 할일 리스트를 반환하는 메소드
    ArrayList getProperList(int category) {
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

    // 요소가 선택(체크)되어있는지를 전체 혹은 카테고리 리스트의 동일한 할일에도 적용해주는 메소드
    void setOtherCategory(boolean selected, String category2, String name) {
        // 만약 현재 표시되는 카테고리가 전체할일이라면
        if (nowCategory == 0) {
            // 유저가 터치한 할일의 카테고리가 집안일 / 숙제 / 음식 / 기타 라면
            switch (category2) {
                case "집안일":
                    // 해당 카테고리 리스트의 전체 요소중
                    for (int j = 0; j < se.getHouse_work_list().size(); j++) {
                        // 유저가 터치한 할일과 같은 이름의 할일을 찾아서
                        if (se.getHouse_work_list().get(j).getWork_name().equals(name)) {
                            // 해당 할일의 선택여부를 바꿔준다
                            se.getHouse_work_list().get(j).setSelected(selected);
                            break;
                        }
                    }
                case "숙제":
                    for (int j = 0; j < se.getHome_work_list().size(); j++) {
                        if (se.getHome_work_list().get(j).getWork_name().equals(name)) {
                            se.getHome_work_list().get(j).setSelected(selected);
                            break;
                        }
                    }
                case "음식":
                    for (int j = 0; j < se.getEating_list().size(); j++) {
                        if (se.getEating_list().get(j).getWork_name().equals(name)) {
                            se.getEating_list().get(j).setSelected(selected);
                            break;
                        }
                    }
                case "기타":
                    for (int j = 0; j < se.getEtc_list().size(); j++) {
                        if (se.getEtc_list().get(j).getWork_name().equals(name)) {
                            se.getEtc_list().get(j).setSelected(selected);
                            break;
                        }
                    }
            }
        // 만약 현재 표시되는 카테고리가 전체할일이 아니라면
        } else {
            // 전체할일 리스트의 모든 요소중
            for (int j = 0; j < se.getWorkList().size(); j++) {
                // 유저가 터치한 할일과 같은 이름의 할일을 찾아서
                if (se.getWorkList().get(j).getWork_name().equals(name)) {
                    // 해당 할일의 선택여부를 바꿔준다
                    se.getWorkList().get(j).setSelected(selected);
                    break;
                }
            }
        }
    }

    // setOtherCategory와 같은 동작구조에서 선택여부 변경이아닌 요소 삭제기능
    void deleteOtherCategory(String category2, String name) {
        if (nowCategory == 0) {
            switch (category2) {
                case "집안일":
                    for (int j = 0; j < se.getHouse_work_list().size(); j++) {
                        if (se.getHouse_work_list().get(j).getWork_name().equals(name)) {
                            se.getHouse_work_list().remove(j);
                            break;
                        }
                    }
                case "숙제":
                    for (int j = 0; j < se.getHome_work_list().size(); j++) {
                        if (se.getHome_work_list().get(j).getWork_name().equals(name)) {
                            se.getHome_work_list().remove(j);
                            break;
                        }
                    }
                case "음식":
                    for (int j = 0; j < se.getEating_list().size(); j++) {
                        if (se.getEating_list().get(j).getWork_name().equals(name)) {
                            se.getEating_list().remove(j);
                            break;
                        }
                    }
                case "기타":
                    for (int j = 0; j < se.getEtc_list().size(); j++) {
                        if (se.getEtc_list().get(j).getWork_name().equals(name)) {
                            se.getEtc_list().remove(j);
                            break;
                        }
                    }
            }
        } else {
            for (int j = 0; j < se.getWorkList().size(); j++) {
                if (se.getWorkList().get(j).getWork_name().equals(name)) {
                    se.getWorkList().remove(j);
                    break;
                }
            }
        }
    }

    void deleteStrList(String name) {
        se.getStrList().remove(se.getStrList().indexOf(name));
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

    void deleteItem(String name) {
        switch (nowCategory) {
            case 0 :
                for (int j = 0; j < se.getWorkList().size(); j++) {
                    if (se.getWorkList().get(j).getWork_name().equals(name)) {
                        se.getWorkList().remove(j);
                        break;
                    }
                }
            case 1 :
                for (int j = 0; j < se.getHouse_work_list().size(); j++) {
                    if (se.getHouse_work_list().get(j).getWork_name().equals(name)) {
                        se.getHouse_work_list().remove(j);
                        break;
                    }
                }
            case 2 :
                for (int j = 0; j < se.getHome_work_list().size(); j++) {
                    if (se.getHome_work_list().get(j).getWork_name().equals(name)) {
                        se.getHome_work_list().remove(j);
                        break;
                    }
                }
            case 3 :
                for (int j = 0; j < se.getEating_list().size(); j++) {
                    if (se.getEating_list().get(j).getWork_name().equals(name)) {
                        se.getEating_list().remove(j);
                        break;
                    }
                }
            case 4 :
                for (int j = 0; j < se.getEtc_list().size(); j++) {
                    if (se.getEtc_list().get(j).getWork_name().equals(name)) {
                        se.getEtc_list().remove(j);
                        break;
                    }
                }
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { // getView메소드는 아이템을 생성(화면에 표시)할때 실행하는 메소드
        house_work_item = layoutInflater.inflate(R.layout.house_work_item, null);
        ImageView iv_category = house_work_item.findViewById(R.id.iv_hw_category);
        TextView tv_work_name = house_work_item.findViewById(R.id.tv_hw_work_name);
        ImageView iv_selected = house_work_item.findViewById(R.id.iv_hw_selected);
        LinearLayout ll_item = house_work_item.findViewById(R.id.ll_hw_item);

        iv_selected.startAnimation(setVisible(i)); // 현재 아이템이 선택되어있는지 표시

        // 아이템 자체에 se의 delete가 true일때만 반응하는 온클릭을 달아서 할일 삭제 구현
        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (se.getDelete()) {
                    deleteOtherCategory(workArrayList.get(i).getCategory(), workArrayList.get(i).getWork_name());
                    deleteStrList(workArrayList.get(i).getWork_name());
                    deleteItem(workArrayList.get(i).getWork_name());
                    HouseWorkActivity.updateListView(nowCategory);
                }
            }
        });

        // 체크아이콘이 터치될때 표시와 선택여부가 바뀌도록 온클릭 구현
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

        // 카테고리에 따라서 다른 색으로 표시되도록 설정
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