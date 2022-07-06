package com.tnedutsledom.modelstudent.FirebaseAdapter;

public class ChildAdapter {
    //자녀의 정보 데이터베이스에 올리는 데이터 셋
    String name;
    String phone_number;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildAdapter(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }

}
