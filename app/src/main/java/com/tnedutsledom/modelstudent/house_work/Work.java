package com.tnedutsledom.modelstudent.house_work;

public class Work {

    // 할일 이름
    String work_name;

    // 카테고리
    // 집안일 / 숙제 / 음식 / 기타
    String category;

    // 사용자가 선택(체크) 해놓은 상태인가
    boolean selected = true;

    // 할일 메모
    String memo = "";
    boolean showMemo = false;
    // 생성자 메소드
    public Work(String work_name, String category, Boolean selected, String memo) {
        this.work_name = work_name;
        this.category = category;
        this.selected = selected;
        this.memo = memo;
    }

    // 게터 세터 영역
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
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

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setShowMemo(boolean showMemo) {
        this.showMemo = showMemo;
    }

    public boolean getShowMemo() {
        return showMemo;
    }
}
