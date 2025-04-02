package com.toki.openapiserver.area.dto;

public record AreaResponseBody(
        Items items,
        int numOfRows,
        int pageNo,
        int totalCount
) {
}
