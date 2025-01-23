package com.example.ProductServices.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements  FileService {

    @Override
    public String uploadImage(String path, MultipartFile imageFile) throws IOException {
        //file name of current file/original name
        String originalFilename = imageFile.getOriginalFilename();

        //Generate a unique file name
        String randomUUID = UUID.randomUUID().toString();
        String fileName = randomUUID.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path+ File.separator+fileName;

        //check if path exists and create
        File folder = new File(path);

        if(!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(imageFile.getInputStream(), Path.of(filePath));

        return fileName;
    }
}
