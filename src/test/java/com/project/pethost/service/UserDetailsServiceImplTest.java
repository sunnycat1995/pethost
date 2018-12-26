package com.project.pethost.service;

import com.project.pethost.dbo.UserDbo;
import com.project.pethost.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext context;
    /*@Spy
    private UserRoleRepository userRoleRepository;
    @Spy
    private RoleRepository roleRepository;*/

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNotFound() {
        userDetailsService.loadUserByUsername("not_found_username");
    }

    @Test
    public void loadUserByUsername() {
        userRepository = context.getBean(UserRepository.class);
        doReturn(new UserDbo()).when(userRepository).findByEmail("irinavyshnikova@gmail.com");
        userDetailsService.loadUserByUsername("irinavyshnikova@gmail.com");
    }
}