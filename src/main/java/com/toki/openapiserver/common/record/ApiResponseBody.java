package com.toki.openapiserver.common.record;

public record ApiResponseBody(
        Items items,
        int numOfRows,
        int pageNo,
        int totalCount
) {
}
