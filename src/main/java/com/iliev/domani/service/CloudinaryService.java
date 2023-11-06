package com.iliev.domani.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
public class CloudinaryService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private final Cloudinary cloudinary;


    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File tmpFile = File.createTempFile(TEMP_FILE,multipartFile.getOriginalFilename());
        multipartFile.transferTo(tmpFile);

        return this.cloudinary.
                uploader().
                upload(tmpFile, Collections.emptyMap())
                .get(URL)
                .toString();
    }
}
