package com.aws.mb.awsmb.Controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class S3Controller {
    private final AmazonS3 amazonS3Client;
    private static final String BUCKET = "m.bezmen-bucket-task2";
    private static final String ROOT_URL = "https://s3.eu-north-1.amazonaws.com/m.bezmen-bucket-task2/";

    public S3Controller(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectRequest request = new PutObjectRequest(BUCKET, file.getOriginalFilename(), file.getInputStream(), metadata);
        PutObjectResult putObjectResult = amazonS3Client.putObject(request);
        model.addAttribute("msg", "Uploaded images: " + file.getOriginalFilename());
        return "imageupload/index";
    }
}
