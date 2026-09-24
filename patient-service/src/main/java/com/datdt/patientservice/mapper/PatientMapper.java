package com.datdt.patientservice.mapper;

import com.datdt.patientservice.dto.PatientRequestDTO;
import com.datdt.patientservice.dto.PatientResponseDTO;
import com.datdt.patientservice.model.Patient;
import org.hibernate.AssertionFailure;

import java.time.LocalDate;

public class PatientMapper {
    private PatientMapper(){
        throw new AssertionError();
    }

    public static PatientResponseDTO toDTO(Patient patient){
        return PatientResponseDTO.builder()
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .id(patient.getId().toString())
                .name(patient.getName())
                .email(patient.getEmail())
                .build();
    }

    public static Patient toModel(PatientRequestDTO requestDTO){
        return Patient.builder()
                .address(requestDTO.getAddress())
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .dateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()))
                .registeredDate(LocalDate.parse(requestDTO.getRegisterDate()))
                .build();
    }

}
