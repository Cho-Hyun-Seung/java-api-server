package com.toki.openapiserver.area.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toki.openapiserver.area.domain.Area;
import com.toki.openapiserver.area.dto.*;
import com.toki.openapiserver.area.repository.AreaRepository;

import com.toki.openapiserver.common.record.ApiResponse;
import com.toki.openapiserver.common.record.ApiResponseHeader;

import com.toki.openapiserver.common.record.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final ModelMapper modelMapper;

    @Value("${open-api.api-key}")
    private String openApiKey;

    //
    /* 루트 지역 GET*/
    protected List<AreaDTO> getRootArea() {
        List<AreaDTO> areaDtoList = new ArrayList<>();
        // 2. 데이터 가져오기
        //  2.1. 객체 생성
        /* TODO baseURL을 항상 동일한것 을 사용하므로 객체 생성하는 코드 분리하기!*/
        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        //  2.2. 객체 값 입력
        ApiResponse areaResponse = restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/B551011/KorService1/areaCode1")
                                .queryParam("serviceKey", openApiKey)
                                .queryParam("pageNo", 1)
                                .queryParam("MobileOS", "ETC")
                                .queryParam("MobileApp", "GULHAN")
                                .queryParam("_type", "json")
//                                .queryParam("areaCode", 1)
                                .queryParam("numOfRows", 300)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ApiResponse.class);

        Response response = areaResponse.response();
        ApiResponseHeader header = response.header();
        /* 오류가 발생 한 경우*/
        if (!header.resultCode().equals("0000")) {
            throw new RuntimeException(header.resultMsg());
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Item> itemList = mapper.convertValue(response.body().items().item(),
                new TypeReference<List<Item>>() {});

        for (Item item : itemList) {
            int code = item.code();
            String name = item.name();

            AreaDTO area = AreaDTO.builder()
                    .areaCode(code)
                    .areaName(name)
                    .parentAreaId(null)
                    .build();

            areaDtoList.add(area);
        }
        return areaDtoList;
    }

    protected List<AreaDTO> getChildArea(List<AreaDTO> rootAreaDtoList) {
        List<AreaDTO> areaDtoList = new ArrayList<>();
        // 2. 데이터 가져오기
        //  2.1. 객체 생성
        /* TODO baseURL을 항상 동일한것 을 사용하므로 객체 생성하는 코드 분리하기!*/
        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        for (AreaDTO rootAreaDTO : rootAreaDtoList) {
            /* 루트 지역 ID 가져오기 */
            Area parentArea = areaRepository.findByAreaName(rootAreaDTO.getAreaName());
            Integer parentAreaId = parentArea.getAreaId();

            //  2.2. 객체 값 입력
            ApiResponse areaResponse = restClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/B551011/KorService1/areaCode1")
                                    .queryParam("serviceKey", openApiKey)
                                    .queryParam("pageNo", 1)
                                    .queryParam("MobileOS", "ETC")
                                    .queryParam("MobileApp", "GULHAN")
                                    .queryParam("_type", "json")
                                    .queryParam("areaCode", parentArea.getAreaCode())
                                    .queryParam("numOfRows", 300)
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ApiResponse.class);

            Response response = areaResponse.response();
            ApiResponseHeader header = response.header();
            /* 오류가 발생 한 경우*/
            if (!header.resultCode().equals("0000")) {
                throw new RuntimeException(header.resultMsg());
            }

            ArrayList<Item> itemList = (ArrayList<Item>) response.body().items().item();

            /* TODO 여기서 200 번 코드가 오지 않을 경우가 존재함! */
            // 3. JSON to DTO
            for (Item item : itemList) {
                int code = item.code();
                String name = item.name();

                AreaDTO area = AreaDTO.builder()
                        .areaCode(code)
                        .areaName(name)
                        .parentAreaId(parentAreaId)
                        .build();

                areaDtoList.add(area);
            }
        }
        return areaDtoList;
    }

    /* 자식 지역 갱신 요청 */
    @Transactional
    public void saveChildArea() {
        List<AreaDTO> rootAreaDtoList = getRootArea();
        List<AreaDTO> areaDTOList = getChildArea(rootAreaDtoList);

        List<Area> areaList = areaDTOList.stream()
                .map(areaDto -> {
                    Area area = new Area();
                    area.setAreaCode(areaDto.getAreaCode());
                    area.setAreaName(areaDto.getAreaName());
                    Area parentArea = null;
                    if (areaDto.getParentAreaId() != null) {
                        parentArea = areaRepository.findByAreaId(areaDto.getParentAreaId());
                    }
                    area.setParentArea(parentArea);
                    return area;
                })
                .toList();
        areaRepository.saveAll(areaList);
    }


    /* 부모 지역 저장 요청 */
    @Transactional
    public void saveRootArea() {
        List<AreaDTO> areaDtoList = getRootArea();
        List<Area> areaList = areaDtoList.stream()
                .map(area -> modelMapper.map(area, Area.class))
                .toList();

        /* ??? 함수 분리*/
        // 5. db 데이터 입력하기
        areaRepository.saveAll(areaList);

    }
}
