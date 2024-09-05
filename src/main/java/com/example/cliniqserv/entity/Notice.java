package com.example.cliniqserv.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@ToString

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "Notice")

public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Notice(String date, String visit, Long appointmentId, User user) {
        this.date = date;
        this.visit = visit;
        this.appointmentId = appointmentId;
        this.user = user;
    }

    @Getter
    private String date;
    @Getter
    private String visit;
    @Getter
    private Long appointmentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", visit='" + visit + '\'' +
//                ", user=" + user +
                '}';
    }
}
