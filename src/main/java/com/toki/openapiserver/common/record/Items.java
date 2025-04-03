package com.toki.openapiserver.common.record;

import com.toki.openapiserver.area.dto.Item;

import java.util.ArrayList;

public record Items<T>(
//public record Items(
        ArrayList<T> item
) {
}
