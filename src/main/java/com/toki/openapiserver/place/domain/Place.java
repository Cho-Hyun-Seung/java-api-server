package com.toki.openapiserver.place.domain;

import com.toki.openapiserver.area.domain.Area;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeId;

    private String title;
    private String detail;
    private String image;
    private String address;


    @Column(columnDefinition = "POINT")
    private Point location;

    private String restDate;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", restDate='" + restDate + '\'' +
                ", category=" + category +
                ", area=" + area +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

}
