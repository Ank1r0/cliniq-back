package com.example.cliniqserv.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNoPassDTO{

    private Long id;

    private String Name;

    private String Surname;

    private String Login;

    private Date dob;

    private String specialisation;

    private String role;
}