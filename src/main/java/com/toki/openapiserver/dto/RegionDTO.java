package com.toki.openapiserver.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public class RegionDTO {
    private int regionCode;
    @Getter
    private String regionName;
    /* select regionId from region where reionName = '서울' 형태로 부모 키값 가져오기!*/
    private String parentRegionName;
}
