package com.toki.openapiserver.area.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class AreaDTO {
    private int areaCode;
    private String areaName;
    /* select regionId from region where reionName = '서울' 형태로 부모 키값 가져오기!*/
    private Integer parentAreaId;
}
