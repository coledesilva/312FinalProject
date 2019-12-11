package com.example.finalproject;
/**
 * This is the Nasa Media model which stores information about a particular piece of media from
 * NASA image and video library.
 * Uses serializable so that it can be passed in an intent.
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

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
