package com.toki.openapiserver.place.domain;

import com.toki.openapiserver.area.domain.Area;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    private int placeID;

    private String title;
    private String detail;
    private String image;
    private String address;
    private Point location;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    private String restDate;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

}
