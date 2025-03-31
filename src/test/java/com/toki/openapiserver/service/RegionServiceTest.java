package com.toki.openapiserver.service;

import com.toki.openapiserver.service.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @DisplayName("루트 지역 api 테스트")
    @Test
    public void testGetRootArea(){
        regionService.getRootRegion();
    }
}