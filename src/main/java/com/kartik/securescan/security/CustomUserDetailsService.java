package com.kartik.securescan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kartik.securescan.entity.User;
import com.kartik.securescan.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = repository.findByUsername(username)

                .orElseThrow(() ->

                        new UsernameNotFoundException(
                                "User Not Found"));

        return org.springframework.security.core.userdetails.User

                .builder()

                .username(user.getUsername())

                .password(user.getPassword())

                .roles(user.getRole())

                .build();

    }

}