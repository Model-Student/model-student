package com.tnedutsledom.modelstudent.house_work;

public class Work {

    // 할일 이름
    String work_name;

    // 카테고리
    // 집안일 / 숙제 / 음식 / 기타
    String category;

    // 사용자가 선택(체크) 해놓은 상태인가
    boolean selected = true;

    // 생성자 메소드
    public Work(String work_name, String category) {
        this.work_name = work_name;
        this.category = category;
    }

    // 게터 세터 영역
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
