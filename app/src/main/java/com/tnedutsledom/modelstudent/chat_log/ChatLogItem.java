package com.tnedutsledom.modelstudent.chat_log;

public class ChatLogItem {
    private String chat_data;   // 채팅 데이터
    private String time;        // 채팅 전송 시간
    private String nugu_or_kid; // 누구스피커의 발화인지 아이의 발화인지

    public ChatLogItem(String chat_data, String time, String nugu_or_kid) {
        this.chat_data = chat_data;
        this.time = time;
        this.nugu_or_kid = nugu_or_kid;
    }

    public String getChat_data() {
        return chat_data;
    }

    public String getNugu_or_kid() {
        return nugu_or_kid;
    }

    public String getTime() {
        return time;
    }

    public void setChat_data(String chat_data) {
        this.chat_data = chat_data;
    }

    public void setNugu_or_kid(String nugu_or_kid) {
        this.nugu_or_kid = nugu_or_kid;
    }

    public void setTime(String time) {
        this.time = time;
    }
}