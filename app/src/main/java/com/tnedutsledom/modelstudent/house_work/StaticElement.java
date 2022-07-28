package com.tnedutsledom.modelstudent.house_work;

import java.util.ArrayList;

public class StaticElement {

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
