package com.datdt.patientservice.service;

import com.datdt.patientservice.dto.PatientRequestDTO;
import com.datdt.patientservice.dto.PatientResponseDTO;
import com.datdt.patientservice.exception.EmailAlreadyExistsException;
import com.datdt.patientservice.exception.PatientNotFoundException;
import com.datdt.patientservice.mapper.PatientMapper;
import com.datdt.patientservice.model.Patient;
import com.datdt.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        //check email unique
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Patient with email: " + patientRequestDTO.getEmail() + "is already exists.");
        }
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO requestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        if(patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Patient with email: " + requestDTO.getEmail() + "is already exists.");
        }
        patient.setName(requestDTO.getName());
        patient.setEmail(requestDTO.getEmail());
        patient.setAddress(requestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));

        patientRepository.save(patient);

        return PatientMapper.toDTO(patient);
    }

}
