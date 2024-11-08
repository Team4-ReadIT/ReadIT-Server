package com.team4.readit.domain.clustering.dto;

import com.team4.readit.domain.clustering.domain.DataPoint;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ClusteringRequest {
    private List<DataPoint> dataPoints;
    private String algorithm;
    private Map<String, Object> parameters;
}