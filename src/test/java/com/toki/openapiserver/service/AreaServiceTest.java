package com.toki.openapiserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AreaServiceTest {

    @Autowired
    private AreaService areaService;

    @DisplayName("루트 지역 api 테스트")
    @Test
    public void testSaveRootArea(){
        Assertions.assertDoesNotThrow(
                () -> areaService.saveRootArea());
    }

    @DisplayName("자식 지역 api 테스트")
    @Test
    public void testSaveChildArea(){
        Assertions.assertDoesNotThrow(
                () -> areaService.saveChildArea());
    }
}