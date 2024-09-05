package com.example.cliniqserv.controller;

import com.example.cliniqserv.DTO.UserNoPassDTO;
import com.example.cliniqserv.DTO.mapper.AppoUserMapper;
import com.example.cliniqserv.entity.Appointment;
import com.example.cliniqserv.entity.Notice;
import com.example.cliniqserv.extra.CustomResponseError;
import com.example.cliniqserv.extra.Role;
import com.example.cliniqserv.repo.AppoRepo;
import com.example.cliniqserv.repo.MedRecRepo;
import com.example.cliniqserv.repo.NoticeRepo;
import com.example.cliniqserv.repo.UserRepo;
import com.example.cliniqserv.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AppoUserMapper appoUserMapper;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AppoRepo appoRepo;
    @Autowired
    private NoticeRepo noticeRepo;
    private MedRecRepo medRecRepo;

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<List<UserNoPassDTO>> getAllUsers(){
        try{
            List<User> userList = new ArrayList<>();
            System.out.println("List Created");
            userRepo.findAll().forEach(userList::add);
            System.out.println("List size: "+userList.size());

            List<UserNoPassDTO> res = userList.stream().map(appoUserMapper::convertToNoPassDto).collect(Collectors.toList());

            if(userList.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/getAllDoctor")
    public ResponseEntity<List<UserNoPassDTO>> getAllDoctor(){
        try{
            List<User> userList = new ArrayList<>();
            userRepo.findByRole(Role.valueOf("Doctor")).forEach(userList::add);
            List<UserNoPassDTO> res = userList.stream().map(appoUserMapper::convertToNoPassDto).collect(Collectors.toList());

            if(userList.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/getUserById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByID(@PathVariable Long id) throws JsonProcessingException {

        Optional<User> userData = userRepo.findById(id);

        System.out.println("userData.get(): " + userData.get());

        if(userData.isPresent())
        {
            return new ResponseEntity<>(userData.get(),HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/getUserById/{id}/appointments",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Appointment>> getUserByIDAppointments(@PathVariable Long id) throws JsonProcessingException {

        Optional<User> userData = userRepo.findById(id);

        System.out.println("userData.get(): " + userData.get().getAssignedAppointments());

        if(userData.isPresent())
        {
            return new ResponseEntity<>(userData.get().getAssignedAppointments(),HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/getUserByLogin/{login}")
    public ResponseEntity<UserNoPassDTO> getUserByLogin(@PathVariable String login){

        Optional<User> userData = userRepo.findByLogin(login);

        if(userData.isPresent())
        {
            UserNoPassDTO userNoPassDTO = appoUserMapper.convertToNoPassDto(userData.get());
            return new ResponseEntity<>(userNoPassDTO,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/addUsers")
    public ResponseEntity<String> saveAppointments(@RequestBody List<User> userData)
    {
        userRepo.saveAll(userData);
        return ResponseEntity.ok("Data saved");
    }

    @PostMapping(path = "/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User userObj = userRepo.save(user);

        return new ResponseEntity<>(userObj,HttpStatus.OK);
    }

    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User newUserData){
        Optional<User> oldUserData = userRepo.findById(id);

        if(oldUserData.isPresent()) {
            try {
                User updatedUserData = oldUserData.get();
                if(newUserData.getLogin()!= null)
                {
                    updatedUserData.setLogin(newUserData.getLogin());
                }
                if(newUserData.getPassword()!= null)
                {
                    updatedUserData.setPassword(passwordEncoder.encode(newUserData.getPassword()));
                }
                if(newUserData.getName()!= null)
                {
                    updatedUserData.setName(newUserData.getName());
                }
                if(newUserData.getSurname()!= null)
                {
                    updatedUserData.setSurname(newUserData.getSurname());
                }

                if(newUserData.getDob()!= null)
                {
                    updatedUserData.setDob(newUserData.getDob());
                }
                if(newUserData.getRole()!= null)
                {
                    updatedUserData.setRole(newUserData.getRole());
                }

                if(newUserData.getSpecialisation()!= null)
                {
                    updatedUserData.setSpecialisation(newUserData.getSpecialisation());
                }

                if(newUserData.getAssignedAppointments()!= null)
                {
                    updatedUserData.setAssignedAppointments(newUserData.getAssignedAppointments());
                }

                User userObj = userRepo.save(updatedUserData);
                System.out.println(updatedUserData);

                System.out.println("updatedUserData: " + updatedUserData);

                return new ResponseEntity<>(userObj,HttpStatus.OK);
                // return new ResponseEntity<>(appoUserMapper.convertToDto(userObj),HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateUserById/{id}/addNotice")
    public ResponseEntity<User> addNotice(@PathVariable Long id, @RequestBody Notice newNoticeData){
        Optional<User> userData = userRepo.findById(id);
        System.out.println("addNotice");

        if(userData.isPresent()) {
            try {

                User updatedUserData = userData.get();
                updatedUserData.addNotice(newNoticeData.getDate(), newNoticeData.getVisit(), newNoticeData.getAppointmentId());
                User userObj =  userRepo.save(updatedUserData);

                System.out.println("after add notice and save");

                return new ResponseEntity<>(userObj,HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateUserById/{id}/removeNotice/{noticeId}")
    public ResponseEntity<User> removeNotice(@PathVariable Long id, @PathVariable Long noticeId){
        Optional<User> userData = userRepo.findById(id);

        if(userData.isPresent()) {
            try {
                User updatedUserData = userData.get();
                System.out.println("updatedUserData" + updatedUserData);
                updatedUserData.removeNotice(noticeId);
                System.out.println("removeNotice");

                User userObj = userRepo.save(updatedUserData);

                System.out.println("after remove notice and save");

                return new ResponseEntity<>(userObj,HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/updateUserWithAppoById/{userId}/appo/{appoId}")
    public ResponseEntity assignAppoToUser(@PathVariable Long userId, @PathVariable Long appoId, @RequestBody User newUserData){
        Optional<User> userData = userRepo.findById(userId);
        Optional<Appointment> appointmentData = appoRepo.findById(appoId);
        System.out.println("newUserData: " + newUserData);

        User updatedUserData = userData.isPresent() && appointmentData.isPresent() ? userData.get() : null;

        if (updatedUserData == null) {
            return new ResponseEntity<>("User already has this appointment", HttpStatus.BAD_REQUEST);
        }

        try {
            Appointment updatedAppointmentData = appointmentData.get();
            List<Appointment> appointmentSet = updatedUserData.getAssignedAppointments();
            AtomicBoolean flag = new AtomicBoolean(false);

            updatedUserData.getAssignedAppointments().forEach((el) -> {
                if (Objects.equals(el.getId(), appoId)) {
                    System.out.println("USER DATA ERROR 1");
                    flag.set(true);
                };
            });

            if (flag.get()) {
                return new ResponseEntity<>("User already has this appointment", HttpStatus.BAD_REQUEST);
            }

            appointmentSet.add(updatedAppointmentData);
            updatedUserData.setAssignedAppointments(appointmentSet);

            int length = (int) newUserData.getCalendarList().stream().count();
            if (length > 0) {
                updatedUserData.addNotice(newUserData.getCalendarList().get(length - 1).getDate(), newUserData.getCalendarList().get(length - 1).getVisit(), newUserData.getCalendarList().get(length - 1).getAppointmentId());
            }

            userRepo.save(updatedUserData);

            return new ResponseEntity<>(updatedUserData, HttpStatus.OK);

        } catch (Exception ignored)
        {
            System.out.println("USER DATA ERROR 2");
            return null;
        }
    }

    @DeleteMapping("/deleteAppoFromUserById/{userId}/appo/{appoId}")
    public ResponseEntity deleteAppoFromUserById(@PathVariable Long userId, @PathVariable Long appoId){
        Optional<User> userData = userRepo.findById(userId);
        Optional<Appointment> appointmentData = appoRepo.findById(appoId);

        User updatedUserData = userData.isPresent() && appointmentData.isPresent() ? userData.get() : null;

        if (updatedUserData == null) {
            return new ResponseEntity<>("User have not this appointment yet", HttpStatus.BAD_REQUEST);
        }

        try {
            Appointment updatedAppointmentData = appointmentData.get();
            List<Appointment> appointmentSet = updatedUserData.getAssignedAppointments();
            AtomicBoolean flag = new AtomicBoolean(false);

            updatedUserData.getAssignedAppointments().forEach((el) -> {
                if (Objects.equals(el.getId(), appoId)) {
                    flag.set(true);
                }
            });

            if (!flag.get()) {
                return new ResponseEntity<>("User have not this appointment yet", HttpStatus.BAD_REQUEST);
            }

            appointmentSet.remove(updatedAppointmentData);
            updatedUserData.setAssignedAppointments(appointmentSet);

            userRepo.save(updatedUserData);
            appoRepo.deleteById(appoId);

            return new ResponseEntity<>(updatedUserData, HttpStatus.OK);

        } catch (Exception ignored)
        {
            System.out.println("USER DATA ERROR 2");
            return null;
        }
    }

    @PatchMapping("/updateUserWithAppoById/{userId}/addAppo")
    public User assignAppoToUser(@PathVariable Long userId, @RequestBody Appointment newAppointmentData){

        System.out.println("newAppointmentData " + newAppointmentData);

        Optional<User> userData = userRepo.findById(userId);
        Appointment appoObj = appoRepo.save(newAppointmentData);

        if(userData.isPresent()) {
            try {
                User updatedUserData = userData.get();
                List<Appointment> appointmentSet = updatedUserData.getAssignedAppointments();
                appointmentSet.add(appoObj);

                System.out.println(appointmentSet);

                updatedUserData.setAssignedAppointments(appointmentSet);
                User updatedUser = userRepo.save(updatedUserData);

                System.out.println("new user: " + updatedUser.getAssignedAppointments());

                return updatedUser;
            } catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }
        }

        return null;
    }

    @PatchMapping("/updateUserWithAppoById/{userId}/removeAppo/{appoId}")
    public User removeAppoToUser(@PathVariable Long userId, @PathVariable Long appoId){
        Optional<User> userData = userRepo.findById(userId);

        if(userData.isPresent()) {
            try {
                User updatedUserData = userData.get();
                List<Appointment> appointmentSet = updatedUserData.getAssignedAppointments();
                appointmentSet.removeIf(p -> Objects.equals(p.getId(), appoId));

                updatedUserData.removeNoticeByAppoId(appoId);


                System.out.println(appointmentSet);

                updatedUserData.setAssignedAppointments(appointmentSet);
                User updatedUser = userRepo.save(updatedUserData);

                System.out.println("new user: " + updatedUser.getAssignedAppointments());

                return updatedUser;
            } catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }
        }

        return null;
    }

    @DeleteMapping(path = "deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id){

        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
