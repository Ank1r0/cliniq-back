package com.example.cliniqserv.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "MedicalRecord")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private Long id;

    private String aboutAppo;

    @OneToOne(mappedBy = "medicalRecord")
    private Appointment appointment;

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", aboutAppo='" + aboutAppo + '\'' +
//                ", appointment=" + appointment +
                '}';
    }
}
