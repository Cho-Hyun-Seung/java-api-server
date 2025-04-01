package com.toki.openapiserver.mapper;

import com.toki.openapiserver.domain.Area;
import com.toki.openapiserver.dto.AreaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaMapper INSTANCE = Mappers.getMapper(AreaMapper.class);

    Area areaDtoToArea(AreaDTO areaDTO);
}
