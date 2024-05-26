package com.example.cliniqserv.repo;

import com.example.cliniqserv.entity.MedicalRecord;
import com.example.cliniqserv.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepo extends JpaRepository<Notice,Long> {
}
