package com.toki.openapiserver.common.record;

public record ApiResponseHeader(
        String resultCode,
        String resultMsg
) {
}
