package com.example.ProductServices.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImage(String path, MultipartFile imageFile) throws IOException;
}
