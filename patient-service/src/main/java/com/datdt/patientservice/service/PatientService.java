package com.datdt.patientservice.service;

import com.datdt.patientservice.dto.PatientRequestDTO;
import com.datdt.patientservice.dto.PatientResponseDTO;
import com.datdt.patientservice.mapper.PatientMapper;
import com.datdt.patientservice.model.Patient;
import com.datdt.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }

}
