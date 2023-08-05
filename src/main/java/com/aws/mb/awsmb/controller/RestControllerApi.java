package com.aws.mb.awsmb.controller;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.aws.mb.awsmb.RegionDTO;
import com.aws.mb.awsmb.service.S3ObjectDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class RestControllerApi {
    private final S3ObjectDataService s3ObjectDataService;
    private final AmazonEC2 amazonEC2;

    public RestControllerApi(S3ObjectDataService s3ObjectDataService, AmazonEC2 amazonEC2) {
        this.s3ObjectDataService = s3ObjectDataService;
        this.amazonEC2 = amazonEC2;
    }

    @GetMapping
    public RegionDTO getMyRegion() {
//        AccessControlList bucketAcl = amazonS3Client.getBucketAcl("app-bucket");
        DescribeRegionsResult describeRegionsResult = amazonEC2.describeRegions();
        return new RegionDTO(amazonEC2.describeRegions(), amazonEC2.describeAvailabilityZones().getAvailabilityZones());
    }

    @GetMapping("/file")
    public String getFile(@RequestParam("image") String key) {
        return s3ObjectDataService.getFile(key);
    }

    @GetMapping("/files")
    public String getFiles(@RequestParam("image") String key) {
        return s3ObjectDataService.getFiles();
    }
}
