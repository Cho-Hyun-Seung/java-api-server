package com.toki.openapiserver.place.domain;

import com.toki.openapiserver.area.domain.Area;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Entity
@NoArgsConstructor
public class Place {
    @Id
    private int placeID;

    private String title;
    private String detail;
    private String image;
    private String address;
    private Point location;
    /* TODO 카테고리 만들고 하기 ! */
//    private Category category;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
}
