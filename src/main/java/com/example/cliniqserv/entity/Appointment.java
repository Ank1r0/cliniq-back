package com.example.cliniqserv.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String videoCallCode;

    @ManyToMany(mappedBy = "appointmentsP",fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> patientSet;

    @ManyToMany(mappedBy = "appointmentsD",fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> doctorSet;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    public void setId(Long id) {
        this.id = id;
    }

    public void setVideoCallCode(String videoCallCode) {
        this.videoCallCode = videoCallCode;
    }

    public void addPatient(User patient) {
        patientSet.add(patient);
    }

    public void addDoctor(User doctor) {
        doctorSet.add(doctor);
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", videoCallCode='" + videoCallCode + '\'' +
                ", patientSet=" + patientSet +
                ", doctorSet=" + doctorSet +
                ", medicalRecord=" + medicalRecord +
                '}';
    }
}
