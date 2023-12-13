package com.example.cliniqserv.repo;

import com.example.cliniqserv.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedRecRepo extends JpaRepository<MedicalRecord,Long> {
}
