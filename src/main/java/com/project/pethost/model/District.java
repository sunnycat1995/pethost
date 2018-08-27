package com.project.pethost.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "district")
public class District {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private City city;
    private String name;
}
