package com.tnedutsledom.modelstudent.chat_log;

public class ChatLogItem {
    private String chat_data;
    private String time;
    private String nugu_or_kid;

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

}