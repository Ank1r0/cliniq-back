package com.example.cliniqserv.controller;


import com.example.cliniqserv.DTO.UserDTO;
import com.example.cliniqserv.DTO.mapper.AppoUserMapper;
import com.example.cliniqserv.repo.AppoRepo;
import com.example.cliniqserv.repo.MedRecRepo;
import com.example.cliniqserv.repo.UserRepo;
import com.example.cliniqserv.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/User")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final AppoUserMapper appoUserMapper;

    @Autowired
    private UserRepo userRepo;
    private AppoRepo appoRepo;
    private MedRecRepo medRecRepo;



    @GetMapping(path = "getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        try{
            List<User> userList = new ArrayList<>();
            System.out.println("List Created");
            userRepo.findAll().forEach(userList::add);
            System.out.println("List size: "+userList.size());

            List<UserDTO> res = userList.stream().map(appoUserMapper::convertToDto).collect(Collectors.toList());

            if(userList.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            System.out.println("id :" +res.get(0).getId());
            System.out.println("id :" +res.size());
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getUserById/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable Long id){

        Optional<User> userData = userRepo.findById(id);


        if(userData.isPresent())
        {
            UserDTO userDTO = appoUserMapper.convertToDto(userData.get());
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
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

        User userObj = userRepo.save(user);

        return new ResponseEntity<>(userObj,HttpStatus.OK);
    }

    @PostMapping("/updateUserById/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id, @RequestBody User newUserData){

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
                    updatedUserData.setPassword(newUserData.getPassword());
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
                /*updatedUserData.setAppointmentsP(newUserData.getAppointmentsP());
                updatedUserData.setAppointmentsD(newUserData.getAppointmentsD());*/

                User userObj = userRepo.save(updatedUserData);

                return new ResponseEntity<>(appoUserMapper.convertToDto(userObj),HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("USER DATA ERROR");
            }


        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping(path = "deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id){

        userRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
