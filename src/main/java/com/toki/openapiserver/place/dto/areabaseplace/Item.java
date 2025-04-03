package com.toki.openapiserver.place.dto.areabaseplace;

public record Item(
        String contentid,
        String title,
        String firstimage,
        double mapx,
        double mapy,
        String addr1,
        String addr2,
        int areacode,
        int sigungucode,
        int booktour,
        String contenttypeid,
        String createdtime,
        String firstimage2,
        String cpyrhtDivCd,
        int mlevel,
        String modifiedtime,
        int showflag,
        String tel,
        String zipcode,
        String cat1,
        String cat2,
        String cat3
) {
}
