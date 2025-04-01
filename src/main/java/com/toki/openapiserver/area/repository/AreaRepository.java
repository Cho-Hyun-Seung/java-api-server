package com.toki.openapiserver.area.repository;

import com.toki.openapiserver.area.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AreaRepository extends JpaRepository<Area, Integer> {
    Area findByAreaName(String areaName);

    Area findByAreaId(Integer parentAreaId);
}
