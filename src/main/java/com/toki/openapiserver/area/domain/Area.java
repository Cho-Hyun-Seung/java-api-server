package com.toki.openapiserver.area.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;

    @Setter
    private int areaCode;

    @Setter
    private String areaName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)  // 필요한 경우에 부모 객체 가져옴!!
    @JoinColumn(name = "parent_area_id")
    private Area parentAreaId;

}
