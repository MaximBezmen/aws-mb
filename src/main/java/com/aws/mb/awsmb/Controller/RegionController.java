package com.aws.mb.awsmb.Controller;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3Client;
import com.aws.mb.awsmb.RegionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class RegionController {

    private final AmazonEC2Client amazonS3Client;

    public RegionController(AmazonEC2Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @GetMapping
    public RegionDTO getMyRegion() {
        ClientConfiguration clientConfiguration = amazonS3Client.getClientConfiguration();
//        com.amazonaws.services.s3.model.Region region1 = amazonS3Client.getRegion();
        // When running on an Amazon EC2 instance, this method
        // will tell you what region your application is in
        Region region = Regions.getCurrentRegion();

        // If you aren’t running in Amazon EC2, then region will be null
        // and you can set whatever default you want to use for development
        if (region == null) region = Region.getRegion(Regions.US_WEST_1);
        return new RegionDTO(region.getName(), region.getAvailableEndpoints());
    }
}
