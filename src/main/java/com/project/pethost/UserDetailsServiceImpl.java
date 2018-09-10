package com.project.pethost;

import com.project.pethost.dbo.RoleDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.UserRoleDbo;
import com.project.pethost.repository.RoleRepository;
import com.project.pethost.repository.UserRepository;
import com.project.pethost.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final UserDbo appUser = this.userRepository.findByEmail(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        final List<UserRoleDbo> userRoles = this.userRoleRepository.findAllById(appUser.getId());

        final List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (userRoles != null) {
            userRoles.forEach(r -> {
                final RoleDbo roleDbo = this.roleRepository.findAllById(r.getId());
                GrantedAuthority authority = new SimpleGrantedAuthority(roleDbo.getRole().getRole());
                grantList.add(authority);
            });
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), appUser.getPassword(), grantList);

        return userDetails;
    }

}
