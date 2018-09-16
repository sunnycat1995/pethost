package com.project.pethost.dto.location;

import lombok.Data;

@Data
public class DistrictDto {
    private Long id;
    private CityDto city;
    private String name;
}
