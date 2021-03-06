package com.project.pethost.service;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.OrderStatusDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.repository.AnimalCategoryRepository;
import com.project.pethost.repository.CityRepository;
import com.project.pethost.repository.OrderStatusRepository;
import com.project.pethost.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DataService {
    private final CityRepository cityRepository;
    private final AnimalCategoryRepository animalCategoryRepository;
    private final UserRepository userRepository;
    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public DataService(final CityRepository cityRepository,
                       final AnimalCategoryRepository animalCategoryRepository,
                       final UserRepository userRepository,
                       final OrderStatusRepository orderStatusRepository) {
        this.cityRepository = cityRepository;
        this.animalCategoryRepository = animalCategoryRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
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

    public List<OrderStatusDbo> orderStatuses() {
        final Iterable<OrderStatusDbo> orderStatuses = orderStatusRepository.findAll();
        final List<OrderStatusDbo> orderStatusDbos = new ArrayList<>();
        orderStatuses.forEach(orderStatusDbos::add);
        orderStatusDbos.sort(Comparator.comparing(OrderStatusDbo::getStatus));
        return orderStatusDbos;
    }

    public List<CityDbo> findAllCitiesByName(final String name) {
        return cityRepository.findAllByName(name);
    }

    public UserDbo getCurrentUser(final @AuthenticationPrincipal Principal principal) {
        final String userName = principal.getName();
        return userRepository.findByEmail(userName);
    }

    public OrderStatusDbo findByStatus(final String status) {
        return orderStatusRepository.findByStatus(status);
    }

    public Double recalculateRating(final Double oldRating, final Long counter, final Integer userRating) {
        final Double newRating = (oldRating * counter + userRating) / (counter + 1);
        return newRating;
    }
}
