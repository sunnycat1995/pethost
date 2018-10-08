package com.project.pethost.service;

import com.project.pethost.dbo.AnimalCategoryDbo;
import com.project.pethost.dbo.OrderStatusDbo;
import com.project.pethost.dbo.location.CityDbo;
import com.project.pethost.repository.AnimalCategoryRepository;
import com.project.pethost.repository.CityRepository;
import com.project.pethost.repository.OrderStatusRepository;
import com.project.pethost.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.Principal;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceTest {

    @InjectMocks
    private DataService dataService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AnimalCategoryRepository animalCategoryRepository;
    @Spy
    private CityRepository cityRepository;
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @Mock
    private Principal principal;

    @Test
    public void animalCategories() {
        final List<AnimalCategoryDbo> animalCategories = dataService.animalCategories();
    }

    @Test
    public void cities() {
        final List<CityDbo> cities = dataService.cities();
    }

    @Test
    public void orderStatuses() {
        final List<OrderStatusDbo> orderStatusDbos = dataService.orderStatuses();
    }

    @Test
    public void findAllCitiesByName() {
        final List<CityDbo> cities = dataService.findAllCitiesByName("name");
    }

    @Test
    public void getCurrentUser() {
        when(principal.getName()).thenReturn("name");
        dataService.getCurrentUser(principal);
    }

    @Test
    public void findByStatus() {
        final OrderStatusDbo orderStatus = dataService.findByStatus("Requested");
    }

    @Test
    public void recalculateRating() {
        dataService.recalculateRating(0.0, 0L, 5);
    }
}