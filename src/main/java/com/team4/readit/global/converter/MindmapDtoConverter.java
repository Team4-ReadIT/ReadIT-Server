package com.team4.readit.global.converter;

import com.team4.readit.domain.mindmap.dto.response.MindmapResponseDto;

import java.util.Map;

public class MindmapDtoConverter {

    // Map<String, Object> hierarchy -> MindmapDto로 변환
    public static MindmapResponseDto convertToMindmapDto(Map<String, Object> hierarchy) {
        return new MindmapResponseDto(hierarchy);
    }
}