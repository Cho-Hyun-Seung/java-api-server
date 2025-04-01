package com.toki.openapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class AreaDTO {
    private int areaCode;
    @Getter
    private String areaTitle;
    /* select regionId from region where reionName = '서울' 형태로 부모 키값 가져오기!*/
    private String parentAreaName;
}
