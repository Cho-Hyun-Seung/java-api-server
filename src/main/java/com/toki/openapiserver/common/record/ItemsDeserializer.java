package com.toki.openapiserver.common.record;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.io.IOException;

public class ItemsDeserializer extends JsonDeserializer<Items> {
    @Override
    public Items deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.readValueAsTree();

        // 만약 "items"가 빈 문자열이면 null 반환
        if (node.isTextual() && node.asText().isEmpty()) {
            return null;
        }

        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        return mapper.treeToValue(node, Items.class);
    }
}
