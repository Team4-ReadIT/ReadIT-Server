package com.team4.readit.domain.clustering.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

//@Data
//public class ClusteringResponse {
//    private List<Integer> clusters;
//    private String algorithm;
//    private String error;  // 에러 발생 시 사용
//    private Map<String, Object> parameters;
//}

// .py 받기 전 테스트용
@Data
public class ClusteringResponse {
    private String message;
    private Object receivedData;  // 받은 데이터 확인용
    private String status;
    private String error;  // 에러 발생 시 사용
}