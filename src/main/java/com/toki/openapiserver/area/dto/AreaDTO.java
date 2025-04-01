package com.toki.openapiserver.area.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AreaDTO {
    private int areaCode;
    private String areaName;
    /* select regionId from region where reionName = '서울' 형태로 부모 키값 가져오기!*/
    private Integer parentAreaId;
}
