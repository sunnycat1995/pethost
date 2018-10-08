package com.project.pethost.controller;

import com.project.pethost.converter.dbodto.OrderDboDtoConverter;
import com.project.pethost.repository.KeeperRatingRepository;
import com.project.pethost.repository.PetRatingRepository;
import com.project.pethost.repository.ReviewRepository;
import com.project.pethost.service.DataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MainController.class, secure = false)
//@Import(SecurityConfig.class)
//@ContextConfiguration(classes = {WebConfiguration.class})
@WebAppConfiguration
@EnableWebMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MainController mainController;

    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private KeeperRatingRepository keeperRatingRepository;
    @MockBean
    private PetRatingRepository petRatingRepository;
    @MockBean
    private DataService dataService;
    @MockBean
    private OrderDboDtoConverter orderDboDtoConverter;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();
    }

    @Test
    public void welcomePage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is4xxClientError());
                //.andExpect(mvcResult -> assertEquals("Success", mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void createReview() throws Exception {
        mockMvc.perform(get("/createReview")).andExpect(status().is4xxClientError());
    }

    @Test
    public void saveReview() throws Exception {
        mockMvc.perform(get("/createReview")).andExpect(status().is4xxClientError());
    }

    @Test
    public void error() throws Exception {
        mockMvc.perform(get("/error")).andExpect(status().is5xxServerError());
    }
}