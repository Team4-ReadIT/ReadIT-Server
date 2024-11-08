package com.team4.readit.domain.clustering.domain;

import lombok.Data;
import java.util.List;

@Data
public class DataPoint {
    private List<Double> features;

    public DataPoint(List<Double> features) {
        this.features = features;
    }
}