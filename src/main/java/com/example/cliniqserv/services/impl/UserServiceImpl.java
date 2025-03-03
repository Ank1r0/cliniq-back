package com.example.cliniqserv.services.impl;

import com.example.cliniqserv.repo.UserRepo;
import com.example.cliniqserv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
