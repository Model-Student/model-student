package com.tnedutsledom.modelstudent.house_work;

public class Work {
    String work_name;
    String category;
    boolean selected = true;

    public Work(String work_name, String category) {
        this.work_name = work_name;
        this.category = category;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean getSelected() {
        return selected;
    }

    public String getCategory() {
        return category;
    }

    public String getWork_name() {
        return work_name;
    }
}
