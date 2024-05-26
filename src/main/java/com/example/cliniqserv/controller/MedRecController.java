package com.example.cliniqserv.controller;

import com.example.cliniqserv.DTO.AppointmentDTO;
import com.example.cliniqserv.DTO.MedicalRecordDTO;
import com.example.cliniqserv.entity.Appointment;
import com.example.cliniqserv.entity.MedicalRecord;
import com.example.cliniqserv.repo.AppoRepo;
import com.example.cliniqserv.repo.MedRecRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cliniqserv.DTO.mapper.AppoUserMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicalRecord/")
@CrossOrigin
@RequiredArgsConstructor

//MEDICAL RECORD CAN BE DELETED ONLY WITH APPOINTMENT SO DELETE INSIDE APPOINTMENT AND NOT HERE
public class MedRecController {

    private final AppoUserMapper appoUserMapper;


    @Autowired
    private MedRecRepo medRecRepo;
    @GetMapping(path = "/getAllMedRecords")
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedRecords(){
       try{

            List<MedicalRecord> medRecList = new ArrayList<>();
            medRecRepo.findAll().forEach(medRecList::add);

            List<MedicalRecordDTO> res = medRecList.stream().map(appoUserMapper::convertToDto).collect(Collectors.toList());

            if(medRecList.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getMedRecord/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedRecord(@PathVariable Long id){

        Optional<MedicalRecord> medRecData = medRecRepo.findById(id);

        if(medRecData.isPresent())
        {

            MedicalRecordDTO dto = appoUserMapper.convertToDto(medRecData.get());
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/updateMedRecord/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedRecordById(@PathVariable Long id, @RequestBody MedicalRecord newAppoData){

        Optional<MedicalRecord> oldAppoData = medRecRepo.findById(id);

        if(oldAppoData.isPresent()) {
            try {
                MedicalRecord updatedAppoData = oldAppoData.get();
                if(newAppoData.getAboutAppo() != null)
                {
                    updatedAppoData.setAboutAppo(newAppoData.getAboutAppo());
                }

                MedicalRecord appoObj = medRecRepo.save(updatedAppoData);

                return new ResponseEntity<>(appoUserMapper.convertToDto(appoObj),HttpStatus.OK);
            }
            catch (Exception ignored)
            {
                System.out.println("MEDICAL RECORD DATA ERROR");
            }
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
