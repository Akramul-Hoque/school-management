package com.school.admission.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionApprovedEvent {
    private Long applicationId;
    private Long studentId;
    private String email;
}
