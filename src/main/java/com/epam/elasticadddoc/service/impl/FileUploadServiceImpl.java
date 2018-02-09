package com.epam.elasticadddoc.service.impl;

import com.epam.elasticadddoc.constant.Constant;
import com.epam.elasticadddoc.exception.ElasticException;
import com.epam.elasticadddoc.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    public void uploadFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constant.UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new ElasticException(e);
        }
    }
}
