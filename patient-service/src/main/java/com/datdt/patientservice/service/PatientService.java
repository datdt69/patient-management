package com.datdt.patientservice.service;

import com.datdt.patientservice.dto.PatientRequestDTO;
import com.datdt.patientservice.dto.PatientResponseDTO;
import com.datdt.patientservice.exception.EmailAlreadyExistsException;
import com.datdt.patientservice.exception.PatientNotFoundException;
import com.datdt.patientservice.grpc.BillingServiceGrpcClient;
import com.datdt.patientservice.mapper.PatientMapper;
import com.datdt.patientservice.model.Patient;
import com.datdt.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
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
        log.info("Create new Patient: {}", patient);
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(),patient.getName(), patient.getEmail());
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO requestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));
        //case not change email
        if(patientRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("Patient with email: " + requestDTO.getEmail() + "is already exists.");
        }
        patient.setName(requestDTO.getName());
        patient.setEmail(requestDTO.getEmail());
        patient.setAddress(requestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));

        patientRepository.save(patient);

        return PatientMapper.toDTO(patient);
    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}
