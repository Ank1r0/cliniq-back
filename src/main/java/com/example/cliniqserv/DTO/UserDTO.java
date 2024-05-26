package com.example.cliniqserv.DTO;

import com.example.cliniqserv.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    private List<Appointment> assignedAppointments;
}
