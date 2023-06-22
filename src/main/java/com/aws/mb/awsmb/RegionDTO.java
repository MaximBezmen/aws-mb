package com.aws.mb.awsmb;

import java.util.Collection;

public record RegionDTO (
        String regionName,
        Collection<String> availableEndpoints
)
{
}
