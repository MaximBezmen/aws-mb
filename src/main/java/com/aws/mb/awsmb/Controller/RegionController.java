package com.aws.mb.awsmb.Controller;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.S3Object;
import com.aws.mb.awsmb.RegionDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/")
public class RegionController {
    private static final String BUCKET = "m.bezmen-bucket-task2";
    private final AmazonS3 amazonS3Client;
    private final AmazonEC2 amazonEC2;

    public RegionController(AmazonS3 amazonS3Client, AmazonEC2 amazonEC2) {
        this.amazonS3Client = amazonS3Client;
        this.amazonEC2 = amazonEC2;
    }

    @GetMapping
    public RegionDTO getMyRegion() {
//        AccessControlList bucketAcl = amazonS3Client.getBucketAcl("app-bucket");
        DescribeRegionsResult describeRegionsResult = amazonEC2.describeRegions();
        return new RegionDTO(amazonEC2.describeRegions(), amazonEC2.describeAvailabilityZones().getAvailabilityZones());
    }

    @GetMapping("/files")
    public String getFile(Model model, @RequestParam("image") String key) throws IOException {
        S3Object object = amazonS3Client.getObject(BUCKET, key);

        return object.toString();
    }
}
