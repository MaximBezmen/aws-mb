package com.aws.mb.awsmb.controller;

import com.aws.mb.awsmb.service.S3ObjectDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class S3Controller {
    private final S3ObjectDataService service;

    public S3Controller(S3ObjectDataService service) {
        this.service = service;
    }

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        service.uploadImage(file);
        model.addAttribute("msg", "Uploaded images: " + file.getOriginalFilename());
        return "imageupload/index";
    }
}
