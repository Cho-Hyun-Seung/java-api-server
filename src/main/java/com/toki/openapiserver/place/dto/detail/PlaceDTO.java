package com.toki.openapiserver.place.dto.detail;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceDTO {
    private String contentid;
    private String title;
    private String firstimage;
    private double mapx;
    private double mapy;
    private String addr1;
    private int areacode;
    private int sigungucode;
    private String contentTypeId;
    private String restDate;
    private String overview;
}
