package com.school.admission.service.api;

import com.school.admission.dto.AdmissionApplicationDto;
import com.school.admission.dto.AdmissionApplicationRequest;

public interface AdmissionService {
    AdmissionApplicationDto submit(AdmissionApplicationRequest req);
    Long approveAndConvert(Long applicationId);
    AdmissionApplicationDto getById(Long id);
    void reject(Long id);
}
