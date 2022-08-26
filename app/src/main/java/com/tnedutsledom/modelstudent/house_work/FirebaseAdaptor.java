package com.tnedutsledom.modelstudent.house_work;

public class FirebaseAdaptor {
    // 할일 이름
    String work_name;
    // 카테고리
    // 집안일 / 숙제 / 음식 / 기타
    String category;
    // 선택되어있음
    boolean selected;

    public FirebaseAdaptor(String work_name, String category, boolean selected) {
        this.work_name = work_name;
        this.category = category;
        this.selected = selected;
    }

    public String getWork_name() {
        return work_name;
    }

    public String getCategory() {
        return category;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }


}
