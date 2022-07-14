package com.tnedutsledom.modelstudent.house_work;

public class Work {
    String work_name;
    String category;

    public Work(String work_name, String category) {
        this.work_name = work_name;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getWork_name() {
        return work_name;
    }
}
