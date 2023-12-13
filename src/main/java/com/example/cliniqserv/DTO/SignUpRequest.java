package com.example.cliniqserv.DTO;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;

    private String surname;

    private String login;

    private String password;
}
