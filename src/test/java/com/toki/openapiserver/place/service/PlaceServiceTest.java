package com.toki.openapiserver.place.service;

import com.toki.openapiserver.place.dto.detail.PlaceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceServiceTest {
    @Autowired
    private PlaceService placeService;

    @DisplayName("지역 국문 관광 정보 조회 api 테스트 ")
    @Test
    public void testGetPlace(){
        ArrayList<PlaceDTO> placeDTOList = placeService.getPlace(14, "A0206", "A02060100");
        placeDTOList.forEach(System.out::println);
    }

    @DisplayName("유적지 국문 관광 정보 조회 api 테스트")
    @Test
    public void testGetDetail(){
        ArrayList<PlaceDTO> placeDtoList = new ArrayList<>();
        placeDtoList.add(new PlaceDTO(
                "130105",
                "가천박물관",
                "", // firstimage
                126.6576044255,
                37.418224103,
                "인천광역시 연수구 청량로102번길 40-9",
                2,
                8,
                "14",
                null // overview
        ));
        placeDtoList = placeService.getPlaceDetail(placeDtoList);
        System.out.println("---------------");
        System.out.println(placeDtoList);
    }

}