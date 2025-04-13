package com.toki.openapiserver.common.record;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public record ApiResponseBody(
        @JsonDeserialize(using = ItemsDeserializer.class)
        Items items,
        int numOfRows,
        int pageNo,
        int totalCount
) {
}
