package com.btechviral.android.collegedatabaseapp;

public class Chat {
    private String chat, email;

    public String getChat(){
        return email + ":\n" +chat;
    }

    public Chat(String chat, String email){
        this.chat = chat;
        this.email = email;
    }
}
