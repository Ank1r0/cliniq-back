package com.example.cliniqserv.entity;

import com.example.cliniqserv.extra.Role;
import com.example.cliniqserv.extra.tools;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String Password;

    private String Name;

    private String Surname;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String specialisation;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Appo_patient",joinColumns = {
            @JoinColumn(name = "User_id",referencedColumnName = "id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "Appointment_id",referencedColumnName = "id")
    })
    private Set<Appointment> appointmentsP;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "Appo_doctor",joinColumns = {
            @JoinColumn(name = "User_id",referencedColumnName = "id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "Appointment_id",referencedColumnName = "id")
            })
    private Set<Appointment> appointmentsD;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setRole(Role role) throws Exception {
        this.role = role;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public void setAppointmentsP(Set<Appointment> appointmentsP) {
        this.appointmentsP = appointmentsP;
    }

    public void setAppointmentsD(Set<Appointment> appointmentsD) {
        this.appointmentsD = appointmentsD;
    }

    public void addAppointmentsP(Appointment appointmentP) {
        appointmentsP.add(appointmentP);
    }

    public void addAppointmentsD(Appointment appointmentD) {
        appointmentsD.add(appointmentD);
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Login='" + login + '\'' +
                ", Password='" + Password + '\'' +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", dob=" + dob +
                ", role=" + role +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
