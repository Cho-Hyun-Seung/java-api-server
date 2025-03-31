package com.toki.openapiserver.service;

import com.toki.openapiserver.repository.RegionRepository;
import com.toki.openapiserver.repository.RegionRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RegionService {
    private RegionRepository regionRepository;

    @Value("${open-api.api-key}")
    private String openApiKey;

    public RegionService(RegionRepositoryImpl regionRepository){
        this.regionRepository = regionRepository;
    }
//
    /* 루트 지역 갱신 요청 */
    public void getRootRegion(){
        // 1. db session 가져오기

        // 2. 데이터 가져오기
        //  2.1. 객체 생성
        RestClient restClient = RestClient.create();

        //  2.2. 객체 값 입력
       ResponseEntity<String> result =  restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("http://apis.data.go.kr/B551011/KorService1/areaCode1")
                                .queryParam("serviceKey", openApiKey)
                                .queryParam("pageNo", 1)
                                .queryParam("MobileOS", "ETC")
                                .queryParam("MobileApp", "GULHAN")
                                .queryParam("_type", "json")
                                .queryParam("areaCode", 0).build())
               .accept(MediaType.APPLICATION_JSON)
               .retrieve()
               .toEntity(String.class);
        // 3. dto to domain

        System.out.println(result);
        // 4. db 데이터 입력하기
    }

    /* 자식 지역 갱신 요청*/
}
