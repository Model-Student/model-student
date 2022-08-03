package com.tnedutsledom.modelstudent.house_work;

import java.util.ArrayList;

// 스태틱 요소를 모아놓은 클래스
public class StaticElement {
    // 서로다른 객체를 생성해도 같은 메모리공간을 사용하는 스태틱의 특성을 이용하여 어뎁터와 액티비티사이에서 같은 변수를 공유하도록 사용
    public static ArrayList<Work> workList, house_work_list, home_work_list, eating_list, etc_list;
    public static ArrayList<String> strList;
    public static boolean delete = false;

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
