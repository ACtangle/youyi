package com.how2java.youyi.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by melon on 18-1-6.
 */
public class UploadImageFile {
    MultipartFile image;

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
