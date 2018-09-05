package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "district")
public class DistrictDbo {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private CityDbo city;
    private String name;
}
