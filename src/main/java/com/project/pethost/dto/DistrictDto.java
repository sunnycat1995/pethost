package com.project.pethost.dto;

import lombok.Data;

@Data
public class DistrictDto {
    private Long id;
    private CityDto city;
    private String name;
}
