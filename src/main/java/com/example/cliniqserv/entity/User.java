package com.example.cliniqserv.entity;

import com.example.cliniqserv.extra.Role;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private Long id;

    private String login;

    private String Password;

    private String Name;

    private String Surname;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String specialisation;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<Notice> calendarList = new ArrayList<>();



    @JsonIdentityReference(alwaysAsId = true) // show only id of Appointment
    @JsonProperty("assignedAppointments")

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, targetEntity = Appointment.class)
    @JoinTable(name = "appo_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    @Setter
    @Getter
    private List<Appointment> assignedAppointments = new ArrayList<Appointment>();

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

    public void setRole(Role role) throws Exception { this.role = role; }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addNotice(String day, String visit, Long appointmentId) {

        {
            System.out.println("day: " + day);
            System.out.println("visit: " + visit);
            Notice newNotice = new Notice(day, visit, appointmentId, this);
            this.calendarList.add(newNotice);
       }
    }

    public void removeNotice(Long id) {
        {
            this.calendarList.removeIf(x -> Objects.equals(x.getId(), id));
        }
    }

    public void removeNoticeByAppoId(Long appoId) {
        {
            this.calendarList.removeIf(x -> Objects.equals(x.getAppointmentId(), appoId));
        }
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
                ", calendarList='" + calendarList.toString() + '\'' +

//                ", calendarList='" + calendarList.stream().forEach(s -> System.out.println(s)) + '\'' +
//                ", assignedAppointments='" + assignedAppointments + '\'' +
                '}';
    }


}
