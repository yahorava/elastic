package com.epam.elasticadddoc.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public void uploadFile(MultipartFile file);
}
