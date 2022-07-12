package com.tnedutsledom.modelstudent.FirebaseAdapter;

public class ParentAdapter {
    //보호자의 정보 데이터베이스에 올리는 데이터 셋
    String name;
    String child_name;

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParentAdapter(String name,String child_name){
        this.name = name;
        this.child_name = child_name;
    }
}
