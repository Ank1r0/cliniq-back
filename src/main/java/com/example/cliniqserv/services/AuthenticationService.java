package com.example.cliniqserv.services;

import com.example.cliniqserv.DTO.JwtAuthenticationResponse;
import com.example.cliniqserv.DTO.RefreshTokenRequest;
import com.example.cliniqserv.DTO.SignUpRequest;
import com.example.cliniqserv.DTO.SigninRequest;
import com.example.cliniqserv.entity.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest) throws Exception;

    JwtAuthenticationResponse signIn(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
