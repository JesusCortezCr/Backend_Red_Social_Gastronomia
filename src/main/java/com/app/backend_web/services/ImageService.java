package com.app.backend_web.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.app.backend_web.entities.Image;

public interface ImageService {

    Image uploadImages(MultipartFile file) throws IOException;
    public void deleteImages(Image image) throws IOException;
}
