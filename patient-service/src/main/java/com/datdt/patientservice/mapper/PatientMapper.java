package com.datdt.patientservice.mapper;

import com.datdt.patientservice.dto.PatientResponseDTO;
import com.datdt.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        return PatientResponseDTO.builder()
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .id(patient.getId().toString())
                .name(patient.getName())
                .email(patient.getEmail())
                .build();
    }
}
