package com.team4.readit.global.converter;

import com.team4.readit.domain.mindmap.dto.response.MindmapDto;

import java.util.Map;

public class MindmapDtoConverter {

    // Map<String, Object> hierarchy -> MindmapDto로 변환
    public static MindmapDto convertToMindmapDto(Map<String, Object> hierarchy) {
        return new MindmapDto(hierarchy);
    }
}