package com.project.pethost.service;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.repository.AnimalCategoryRepository;
import com.project.pethost.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DataService {
    private final CityRepository cityRepository;
    private final AnimalCategoryRepository animalCategoryRepository;

    @Autowired
    public DataService(final CityRepository cityRepository,
                       final AnimalCategoryRepository animalCategoryRepository) {
        this.cityRepository = cityRepository;
        this.animalCategoryRepository = animalCategoryRepository;
    }


    public List<AnimalCategoryDbo> animalCategories() {
        final Iterable<AnimalCategoryDbo> animalCategories = animalCategoryRepository.findAll();
        final List<AnimalCategoryDbo> animalCategoryDbos = new ArrayList<>();
        animalCategories.forEach(animalCategoryDbos::add);
        animalCategoryDbos.sort(Comparator.comparing(AnimalCategoryDbo::getCategory));
        return animalCategoryDbos;
    }

    public List<CityDbo> cities() {
        final Iterable<CityDbo> cities = cityRepository.findAll();
        final List<CityDbo> cityDbos = new ArrayList<>();
        cities.forEach(cityDbos::add);
        cityDbos.sort(Comparator.comparing(CityDbo::getName));
        return cityDbos;
    }

    public List<CityDbo> findAllCitiesByName(final String name) {
        return cityRepository.findAllByName(name);
    }
}
