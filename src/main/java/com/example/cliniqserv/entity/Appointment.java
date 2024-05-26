package com.example.cliniqserv.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "Appointment")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id",    scope=Appointment.class
)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String videoCallCode;

     @JsonIdentityReference(alwaysAsId = true) // show only id of User
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "assignedAppointments",fetch = FetchType.LAZY)
    private List<User> assignedUsers = new ArrayList<User>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicalRecord_id", referencedColumnName = "id")
    private MedicalRecord medicalRecord;

    public void setId(Long id) {
        this.id = id;
    }

    public void setVideoCallCode(String videoCallCode) {
        this.videoCallCode = videoCallCode;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", videoCallCode='" + videoCallCode + '\'' +
//                ", medicalRecord=" + medicalRecord.toString() +
                '}';
    }
}
