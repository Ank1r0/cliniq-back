package com.example.cliniqserv.repo;

import com.example.cliniqserv.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppoRepo extends JpaRepository<Appointment,Long> {


}
