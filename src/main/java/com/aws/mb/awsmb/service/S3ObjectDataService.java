package com.aws.mb.awsmb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.aws.mb.awsmb.entity.S3ObjectData;
import com.aws.mb.awsmb.repository.S3ObjectDataRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
public class S3ObjectDataService {
    private static final String ROOT_URL = "https://s3.eu-north-1.amazonaws.com/m.bezmen-bucket-task2/";
    private static final String BUCKET = "m.bezmen-bucket-task2";

    private final AmazonS3 amazonS3Client;
    private final S3ObjectDataRepository repository;
    private final SnSAndSqsService snSAndSqsService;

    public S3ObjectDataService(AmazonS3 amazonS3Client, S3ObjectDataRepository repository, SnSAndSqsService snSAndSqsService) {
        this.amazonS3Client = amazonS3Client;
        this.repository = repository;
        this.snSAndSqsService = snSAndSqsService;
    }

    public void uploadImage(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectRequest request = new PutObjectRequest(BUCKET, file.getOriginalFilename(), file.getInputStream(), metadata);
        PutObjectResult putObjectResult = amazonS3Client.putObject(request);

        S3ObjectData s3ObjectData = new S3ObjectData();
        s3ObjectData.setName(file.getResource().getFilename());
        s3ObjectData.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        s3ObjectData.setSize(file.getSize());

        repository.save(s3ObjectData);

        snSAndSqsService.pushToSqs(s3ObjectData.toString());
    }


    public String getFile(String key){
        S3Object object = amazonS3Client.getObject(BUCKET, key);

        return object.toString();
    }

    public String getFiles() {
        return Arrays.toString(repository.findAll().toArray());
    }
}
