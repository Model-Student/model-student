package com.tnedutsledom.modelstudent.house_work;

import java.util.ArrayList;

// 스태틱 요소를 모아놓은 클래스
public class StaticElement {
    // 서로다른 객체를 생성해도 같은 메모리공간을 사용하는 스태틱의 특성을 이용하여 어뎁터와 액티비티사이에서 같은 변수를 공유하도록 사용

    private static StaticElement staticElement;
    private ArrayList<Work> workList = new ArrayList<>(),
            house_work_list = new ArrayList<>(),
            home_work_list = new ArrayList<>(),
            eating_list = new ArrayList<>(),
            etc_list = new ArrayList<>();
    private ArrayList<String> strList = new ArrayList<>();
    private boolean delete = false;

    private StaticElement() {
    }

    public static StaticElement getInstance() {
        if (staticElement == null)
        {
            synchronized(StaticElement.class)
            {
                staticElement = new StaticElement();
            }
        }

        return staticElement;
    }

    public ArrayList<String> getStrList() {
        return strList;
    }

    public ArrayList<Work> getEating_list() {
        return eating_list;
    }

    public ArrayList<Work> getEtc_list() {
        return etc_list;
    }

    public ArrayList<Work> getHome_work_list() {
        return home_work_list;
    }

    public ArrayList<Work> getHouse_work_list() {
        return house_work_list;
    }

    public ArrayList<Work> getWorkList() {
        return workList;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
