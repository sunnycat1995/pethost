package com.project.pethost.dbo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class LocationIdType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
