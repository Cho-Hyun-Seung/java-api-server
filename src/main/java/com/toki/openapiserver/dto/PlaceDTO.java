package com.toki.openapiserver.dto;

import lombok.Builder;

@Builder
public class PlaceDTO {
    private String title;
    private String firstImage;
    private String addr1;
    private String addr2;
    private int contentId;
    private String useFee;
    private String restDate;
    private PlaceCategoryDTO placeCategoryDTO;
//    private String categoryName;
    /* 도 명칭과 시군구 명칭은 Region과 연결?*/
    private AreaDTO regionDTO;
//    private int areaCode;       // 도 코드
//    private int sigunguCode;    // 시군구 코드
    private double coordX;      // x 좌표
    private double coordY;      // y 좌표
}
