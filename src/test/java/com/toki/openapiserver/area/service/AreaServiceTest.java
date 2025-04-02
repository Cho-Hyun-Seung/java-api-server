package com.toki.openapiserver.area.service;

import com.toki.openapiserver.area.dto.AreaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AreaServiceTest {

    @Autowired
    private AreaService areaService;

    @DisplayName("루트 지역 api 호출 테스트")
    @Test
    public void testGetRootArea() {
        List<AreaDTO> areaDTOList = areaService.getRootArea();
        Assertions.assertNotNull(areaDTOList);

        for (AreaDTO areaDto : areaDTOList) {
            System.out.println(areaDto.toString());
        }
    }

    @DisplayName("자식 지역 api 호출 테스트")
    @Test
    public void testGetChildArea() {
        List<AreaDTO> rootAreaDtoList = new ArrayList<>();
        rootAreaDtoList.add(new AreaDTO(2, "인천", null));
        List<AreaDTO> areaDTOList = areaService.getChildArea(rootAreaDtoList);
        Assertions.assertNotNull(areaDTOList);

        for (AreaDTO areaDto : areaDTOList) {
            System.out.println(areaDto.toString());
        }
    }

    @DisplayName("루트 지역 api 저장 테스트")
    @Test
    public void testSaveRootArea() {
        Assertions.assertDoesNotThrow(
                () -> areaService.saveRootArea());
    }

    @DisplayName("자식 지역 api 테스트")
    @Test
    public void testSaveChildArea() {
        Assertions.assertDoesNotThrow(
                () -> areaService.saveChildArea());
    }
}