package com.example.cliniqserv.DTO;

import com.example.cliniqserv.extra.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String Name;

    private String Surname;

    private String Login;

    private String Password;

    private Date dob;

    private String specialisation;

    private String role;


}
