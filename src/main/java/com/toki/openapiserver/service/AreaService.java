package com.toki.openapiserver.service;

import com.toki.openapiserver.domain.Area;
import com.toki.openapiserver.dto.AreaDTO;
import com.toki.openapiserver.mapper.AreaMapper;
import com.toki.openapiserver.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AreaService {
    private AreaRepository areaRepository;

    @Value("${open-api.api-key}")
    private String openApiKey;

    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    //
    /* 루트 지역 불러오기 */
    @Transactional
    public List<Area> getRootArea() {
        // 1. db session 가져오기

        // 2. 데이터 가져오기
        //  2.1. 객체 생성
        /* TODO baseURL을 항상 동일한것 을 사용하므로 객체 생성하는 코드 분리하기!*/
        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        //  2.2. 객체 값 입력
        String result = restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/B551011/KorService1/areaCode1")
                                .queryParam("serviceKey", openApiKey)
                                .queryParam("pageNo", 1)
                                .queryParam("MobileOS", "ETC")
                                .queryParam("MobileApp", "GULHAN")
                                .queryParam("_type", "json")
//                                .queryParam("areaCode", 1)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);

        /* TODO 여기서 200 번 코드가 오지 않을 경우가 존재함! */
        // 3. JSON to DTO
        JSONParser jsonParser = new JSONParser();
        List<AreaDTO> areaDtoList = new ArrayList<>();
        try {
            JSONObject jsonData = (JSONObject) jsonParser.parse(result);
            JSONObject response = (JSONObject) jsonData.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject itemsWrapper = (JSONObject) body.get("items");
            JSONArray items = (JSONArray) itemsWrapper.get("item");
            for (Object itemObj : items) {
                JSONObject item = (JSONObject) itemObj;
//            System.out.println(Integer.parseInt(item.get("code").toString()));
                int code = Integer.parseInt(item.get("code").toString());
                String name = item.get("name").toString();

                AreaDTO area = AreaDTO.builder()
                        .areaCode(code)
                        .areaTitle(name)
                        .parentAreaName(null)
                        .build();

                areaDtoList.add(area);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 4. dto to domain
//        List<Area> areaList = AreaMapper.INSTANCE.areaDtoToArea(AreaDTO );
        List<Area> areaList = areaDtoList.stream().map(AreaMapper.INSTANCE::areaDtoToArea).toList();

        /* ??? 함수 분리*/
        // 5. db 데이터 입력하기
        areaRepository.saveAll(areaList);
        return areaList;
    }

//    protected void saveArea(List<Area> areaList){
//        areaRepository.saveAll(areaList);
//    }

    /* 자식 지역 갱신 요청*/
}
