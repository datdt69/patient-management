package com.datdt.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PatientResponseDTO {
    private String id;
    private String name;
    private String address;
    private String email;
    private String dateOfBirth;
}
