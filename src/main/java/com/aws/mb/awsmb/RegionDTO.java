package com.aws.mb.awsmb;

import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;

import java.util.List;

public record RegionDTO(
        DescribeRegionsResult regions_response,
        List<AvailabilityZone> availabilityZones
) {
}
