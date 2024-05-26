package com.example.cliniqserv.repo;

import com.example.cliniqserv.DTO.UserNoPassDTO;
import com.example.cliniqserv.entity.User;
import com.example.cliniqserv.extra.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    List<User> findByRole(Role role);
}
