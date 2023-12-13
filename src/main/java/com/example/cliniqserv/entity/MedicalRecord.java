package com.example.cliniqserv.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "MedicalRecord")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String aboutAppo;

    @OneToOne(mappedBy = "medicalRecord")
    private Appointment appointment;

}
