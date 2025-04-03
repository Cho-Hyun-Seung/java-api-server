package com.toki.openapiserver.place.repository;

import com.toki.openapiserver.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}
