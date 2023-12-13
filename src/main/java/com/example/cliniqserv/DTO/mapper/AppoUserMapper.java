package com.example.cliniqserv.DTO.mapper;

import com.example.cliniqserv.DTO.*;
import com.example.cliniqserv.entity.Appointment;
import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.repo.AppoRepo;
import com.example.cliniqserv.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppoUserMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    private UserRepo userRepo;
    private AppoRepo AppoRepo;


    public AppointmentDTO convertToDto(Appointment a) {
        return modelMapper.map(a,AppointmentDTO.class);
    }

    public UserDTO convertToDto(User u) {
        return modelMapper.map(u,UserDTO.class);
    }

    public UserDTO convertToDto(Optional<User> u) {

        return modelMapper.map(u,UserDTO.class);
    }

    public User convertToEntity(UserDTO dto) {
        return modelMapper.map(dto,User.class);
    }
}
