package com.example.cliniqserv.controller;

import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.repo.AppoRepo;
import com.example.cliniqserv.repo.UserRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User/Appointment")
public class UserAppoController {
    private UserRepo userRepo;
    private AppoRepo appoRepo;

    public UserAppoController(UserRepo userRepo, AppoRepo appoRepo) {
        this.userRepo = userRepo;
        this.appoRepo = appoRepo;
    }


}
