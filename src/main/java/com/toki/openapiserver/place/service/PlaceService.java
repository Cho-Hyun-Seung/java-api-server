package com.toki.openapiserver.place.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toki.openapiserver.area.domain.Area;
import com.toki.openapiserver.common.record.ApiResponse;
import com.toki.openapiserver.common.record.ApiResponseHeader;
import com.toki.openapiserver.area.repository.AreaRepository;
import com.toki.openapiserver.common.record.Items;
import com.toki.openapiserver.common.record.Response;

import com.toki.openapiserver.place.domain.CategoryType;
import com.toki.openapiserver.place.domain.Place;
import com.toki.openapiserver.place.dto.areabaseplace.Item;
import com.toki.openapiserver.place.dto.detail.PlaceDTO;
import com.toki.openapiserver.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final AreaRepository areaRepository;
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326); // WGS84
    private final ObjectMapper mapper;


    @Value("${open-api.api-key}")
    private String openApiKey;

    protected ArrayList<PlaceDTO> getPlace(int contentTypeId, String cat2, String cat3) {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        ApiResponse placeResponse = restClient.get().uri(uriBuilder ->
                        uriBuilder.path("/B551011/KorService1/areaBasedSyncList1")
                                .queryParam("serviceKey", openApiKey)
                                .queryParam("pageNo", 1)
                                .queryParam("MobileOS", "ETC")
                                .queryParam("MobileApp", "GULHAN")
                                .queryParam("_type", "json")
                                .queryParam("numOfRows", 3000)
                                .queryParam("contentTypeId", contentTypeId)
                                .queryParam("cat1", "A02")
                                .queryParam("cat2", cat2)
                                .queryParam("cat3", cat3)
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ApiResponse.class);

        Response response = placeResponse.response();
        ApiResponseHeader header = response.header();

        if (!header.resultCode().equals("0000")) {
            throw new RuntimeException(header.resultMsg());
        }


        List<Item> placeList = mapper.convertValue(response.body().items().item(),
                new TypeReference<List<Item>>() {
                });

        ArrayList<PlaceDTO> areaBasePlaceDtoList = new ArrayList<>();
        for (Item place : placeList) {
            PlaceDTO areaBasePlaceDTO = new PlaceDTO();

            /* 하드 코딩 안하는 방법?*/
            areaBasePlaceDTO.setContentid(place.contentid());
            areaBasePlaceDTO.setTitle(place.title());
            areaBasePlaceDTO.setFirstimage(place.firstimage());
            areaBasePlaceDTO.setMapx(place.mapx());
            areaBasePlaceDTO.setMapy(place.mapy());
            areaBasePlaceDTO.setAddr1(place.addr1());
            areaBasePlaceDTO.setAreacode(place.areacode());
            areaBasePlaceDTO.setSigungucode(place.sigungucode());
            areaBasePlaceDTO.setContentTypeId(place.contenttypeid());

            areaBasePlaceDtoList.add(areaBasePlaceDTO);
        }

        return areaBasePlaceDtoList;
    }

    protected ArrayList<PlaceDTO> getPlaceDetail(ArrayList<PlaceDTO> placeList) {
        ArrayList<PlaceDTO> placeDtoList = new ArrayList<>();

        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        for (PlaceDTO place : placeList) {
            ApiResponse placeResponse = restClient.get().uri(uriBuilder ->
                            uriBuilder.path("/B551011/KorService1/detailCommon1")
                                    .queryParam("serviceKey", openApiKey)
                                    .queryParam("pageNo", 1)
                                    .queryParam("MobileOS", "ETC")
                                    .queryParam("MobileApp", "GULHAN")
                                    .queryParam("_type", "json")
                                    .queryParam("numOfRows", 3000)
                                    .queryParam("contentId", place.getContentid())
                                    .queryParam("contentTypeId", place.getContentTypeId())
                                    .queryParam("defaultYN", "N")
                                    .queryParam("firstImageYN", "N")
                                    .queryParam("areacodeYN", "N")
                                    .queryParam("catcodeYN", "N")
                                    .queryParam("addrinfoYN", "N")
                                    .queryParam("mapinfoYN", "N")
                                    .queryParam("overviewYN", "Y")
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ApiResponse.class);

            Response response = placeResponse.response();
            ApiResponseHeader header = response.header();

            if (!header.resultCode().equals("0000")) {
                throw new RuntimeException(header.resultMsg());
            }

            Items items = response.body().items();
            if (items == null || items.item() == null) {
                // 빈 리스트 처리 또는 적절한 예외 처리
                continue; // 또는 다음 로직으로 처리
            }

    
            List<com.toki.openapiserver.place.dto.detail.common.Item> placeInfo = mapper.convertValue(response.body().items().item(),
                    new TypeReference<List<com.toki.openapiserver.place.dto.detail.common.Item>>() {
                    });

            place.setOverview(placeInfo.get(0).overview());
            placeDtoList.add(place);
        }


        return placeDtoList;
    }

    protected ArrayList<PlaceDTO> getPlaceRestDate(ArrayList<PlaceDTO> placeList, CategoryType category) {
        ArrayList<PlaceDTO> placeDtoList = new ArrayList<>();

        RestClient restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        for (PlaceDTO place : placeList) {
            ApiResponse placeResponse = restClient.get().uri(uriBuilder ->
                            uriBuilder.path("/B551011/KorService1/detailIntro1")
                                    .queryParam("serviceKey", openApiKey)
                                    .queryParam("pageNo", 1)
                                    .queryParam("MobileOS", "ETC")
                                    .queryParam("MobileApp", "GULHAN")
                                    .queryParam("_type", "json")
                                    .queryParam("numOfRows", 3000)
                                    .queryParam("contentId", place.getContentid())
                                    .queryParam("contentTypeId", place.getContentTypeId())
                                    .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(ApiResponse.class);

            Response response = placeResponse.response();
            ApiResponseHeader header = response.header();

            if (!header.resultCode().equals("0000")) {
                throw new RuntimeException(header.resultMsg());
            }

    
            if(category.equals(CategoryType.MUSEUM)){
                List<com.toki.openapiserver.place.dto.detail.museum.Item> placeInfo = mapper.convertValue(response.body().items().item(),
                        new TypeReference<List<com.toki.openapiserver.place.dto.detail.museum.Item>>() {
                        });

                place.setRestDate(placeInfo.get(0).restdateculture());
                placeDtoList.add(place);
            }else{
                List<com.toki.openapiserver.place.dto.detail.tourspot.Item> placeInfo = mapper.convertValue(response.body().items().item(),
                        new TypeReference<List<com.toki.openapiserver.place.dto.detail.tourspot.Item>>() {
                        });

                place.setRestDate(placeInfo.get(0).restdate());
                placeDtoList.add(place);
            }
        }


        return placeDtoList;
    }


    @Transactional
    public void insertPlace(int contentTypeId, String cat2, String cat3) {
        ArrayList<PlaceDTO> placeList = getPlace(contentTypeId, cat2, cat3);
        ArrayList<PlaceDTO> integrationPlaceList = getPlaceDetail(placeList);
        CategoryType categoryType = null;

        if(cat3.equals("A02060100")){
            categoryType = CategoryType.MUSEUM;
        }
        if(cat3.equals("A02010700")){
            categoryType = CategoryType.HISTORIC_SITE;
        }
        if(cat3.equals("A02010600")){
            categoryType = CategoryType.FOLK_VILLAGE;
        }
        ArrayList<PlaceDTO> integrationPlaceList2 = getPlaceRestDate(integrationPlaceList, categoryType);

        for (PlaceDTO placeDto : integrationPlaceList2) {
            Area parentArea = areaRepository.findAreaByAreaCodeAndParentAreaIdIsNull(placeDto.getAreacode());
            Area area = areaRepository.findByAreaCodeAndParentAreaId(placeDto.getSigungucode(), parentArea);
            Point point = geometryFactory.createPoint(new Coordinate(placeDto.getMapx(), placeDto.getMapy()));

            Place place = Place.builder()
                    .title(placeDto.getTitle())
                    .detail(placeDto.getOverview())
                    .image(placeDto.getFirstimage())
                    .address(placeDto.getAddr1())
                    .location(point)
                    .category(categoryType)
                    .restDate(placeDto.getRestDate())
                    .area(area)
                    .build();

            placeRepository.save(place);
//            System.out.println(place.toString());
        }
    }

}
