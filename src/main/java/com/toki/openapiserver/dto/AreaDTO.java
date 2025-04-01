package com.toki.openapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {
    private int areaCode;
    @Getter
    private String areaName;
    /* select regionId from region where reionName = '서울' 형태로 부모 키값 가져오기!*/
    private Integer parentAreaId;
}
