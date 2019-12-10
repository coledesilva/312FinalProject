package com.example.finalproject;

import java.io.Serializable;

public class NasaMedia implements Serializable {

    private String title;
    private String description;
    private String mediaType;
    private String mediaLink;

    NasaMedia() {
        this.title = "";
        this.description = "";
        this.mediaType = "";
        this.mediaLink = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }
}
