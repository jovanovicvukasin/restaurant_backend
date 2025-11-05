package com.vukasin.restaurant.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class ImgService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) {
        try {
            if(file.isEmpty()){
                throw new FileNotFoundException("File is empty");
            }

            //create directory if does not exist
            Path directory = Path.of(uploadDir);
            if(!Files.exists(directory)) {
                Files.createDirectory(directory);
            }


            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            //location for file
            Path filePath = directory.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName;


        } catch (IOException e) {
            throw new RuntimeException("Failed", e);
        }
    }
}
