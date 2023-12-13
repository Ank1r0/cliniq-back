package com.example.cliniqserv.controller;

import com.example.cliniqserv.DTO.AppointmentDTO;
import com.example.cliniqserv.DTO.UserDTO;
import com.example.cliniqserv.DTO.mapper.AppoUserMapper;
import com.example.cliniqserv.entity.Appointment;
import com.example.cliniqserv.repo.AppoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// create UserDTO and AppointmentDTO and use them in controllers
@RestController
@RequestMapping("/Appointment")
@RequiredArgsConstructor
@CrossOrigin
public class AppoController {

    private final AppoUserMapper appoUserMapper;


    @Autowired
    private AppoRepo appoRepo;



    @PostMapping("/saveAppo")
    public ResponseEntity<String> saveAppointments(@RequestBody List<Appointment> appoData)
    {
        appoRepo.saveAll(appoData);
        return ResponseEntity.ok("Data saved");
    }

    @PostMapping(path = "/addAppo")
    public ResponseEntity<Appointment> addAppo(@RequestBody Appointment appointment){

        System.out.println("addAppo");


        Appointment appoObj = appoRepo.save(appointment);

        return new ResponseEntity<>(appoObj,HttpStatus.OK);
    }

    @PostMapping("/updateAppoById/{id}")
    public ResponseEntity<AppointmentDTO> updateAppoByID(@PathVariable Long id, @RequestBody Appointment newAppoData){

        Optional<Appointment> oldAppoData = appoRepo.findById(id);

        if(oldAppoData.isPresent()) {
            try {
                Appointment updatedAppoData = oldAppoData.get();
                if(newAppoData.getVideoCallCode() != null)
                {
                    updatedAppoData.setVideoCallCode(newAppoData.getVideoCallCode());
                }
                if(newAppoData.getPatientSet() != null)
                {
                    updatedAppoData.setPatientSet(newAppoData.getPatientSet());
                }
                if(newAppoData.getDoctorSet() != null)
                {
                    updatedAppoData.setDoctorSet(newAppoData.getDoctorSet());
                }
                if(newAppoData.getMedicalRecord() != null)
                {
                    updatedAppoData.setMedicalRecord(newAppoData.getMedicalRecord());
                }

                Appointment appoObj = appoRepo.save(updatedAppoData);

                return new ResponseEntity<>(appoUserMapper.convertToDto(appoObj),HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("APPOINTMENT DATA ERROR");
            }
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/getAppointmentById/{id}")
    public ResponseEntity<AppointmentDTO> getUserByID(@PathVariable Long id){

        Optional<Appointment> appoData = appoRepo.findById(id);

        if(appoData.isPresent())
        {
            AppointmentDTO appointmentDTO = appoUserMapper.convertToDto(appoData.get());
            return new ResponseEntity<>(appointmentDTO,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping(path = "getAllAppointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(){
        try{
            List<Appointment> appoList = new ArrayList<>();
            System.out.println("List Created");
            appoRepo.findAll().forEach(appoList::add);
            System.out.println("List size"+appoList.size());

            List<AppointmentDTO> res = appoList.stream().map(appoUserMapper::convertToDto).collect(Collectors.toList());

            if(appoList.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
