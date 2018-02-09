package com.epam.elasticadddoc.controller;

import com.epam.elasticadddoc.constant.Attribute;
import com.epam.elasticadddoc.constant.Constant;
import com.epam.elasticadddoc.constant.Message;
import com.epam.elasticadddoc.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(Attribute.MESSAGE_ATTR, Message.NO_FILE_TO_UPLOAD);
        } else {
            fileUploadService.uploadFile(file);
            redirectAttributes.addFlashAttribute(Attribute.MESSAGE_ATTR,
                    Message.FILE_UPLOADED + file.getOriginalFilename());
        }
        return Constant.REDIRECT;
    }
}
