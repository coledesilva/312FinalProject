package com.example.finalproject;
/**
 * This is the fetch photo listener interface so that the fetch photo class can be generic
 * and reused throughout our code
 *
 * @authors: Cole & Jackson
 * @version: v1.0
 * @date: 12/11/2019
 */

import android.graphics.Bitmap;

public interface FetchPhotoListener {
    void receivePhotoBitmap(Bitmap bitmap);
}
