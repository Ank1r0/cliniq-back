package com.example.cliniqserv.services.impl;

import com.example.cliniqserv.DTO.JwtAuthenticationResponse;
import com.example.cliniqserv.DTO.RefreshTokenRequest;
import com.example.cliniqserv.DTO.SignUpRequest;
import com.example.cliniqserv.DTO.SigninRequest;
import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.extra.Role;
import com.example.cliniqserv.repo.UserRepo;
import com.example.cliniqserv.services.AuthenticationService;
import com.example.cliniqserv.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;
    public User signup(SignUpRequest signUpRequest) throws Exception {
        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setRole(Role.Patient);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepo.save(user);
    }

    public JwtAuthenticationResponse signIn(SigninRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getLogin(),signinRequest.getPassword()));

        var user = userRepo.findByLogin(signinRequest.getLogin()).orElseThrow(() -> new IllegalArgumentException("Invalid login or password."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest)
    {
        String login = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepo.findByLogin(login).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
