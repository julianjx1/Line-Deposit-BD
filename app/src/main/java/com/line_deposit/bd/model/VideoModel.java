package com.line_deposit.bd.model;

public class VideoModel {
    private String image;
    private String title;
    private String videoReference;

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoReference() {
        return videoReference;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoReference(String videoReference) {
        this.videoReference = videoReference;
    }

    public VideoModel(String title, String image, String videoReference){
        this.image = image;
        this.title = title;
        this.videoReference = videoReference;

    }
}
