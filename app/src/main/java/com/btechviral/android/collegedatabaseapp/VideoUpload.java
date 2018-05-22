package com.btechviral.android.collegedatabaseapp;

class VideoUpload {
    String name, url;

    public String getName(){
        return name;
    }

    public String getUrl() {
        return url;
    }

    public VideoUpload(String name, String url){
        this.name = name;
        this.url = url;
    }
}
