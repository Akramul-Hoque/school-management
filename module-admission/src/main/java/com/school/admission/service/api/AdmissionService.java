package com.school.admission.service.api;

import com.school.admission.dto.AdmissionApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdmissionService {
    AdmissionApplicationDto submitApplication(AdmissionApplicationDto dto);
    AdmissionApplicationDto getApplicationById(Long id);
    Page<AdmissionApplicationDto> getApplicationsByStatus(String status, Pageable pageable);
    
    void approveApplication(Long applicationId, Long sectionId);
    void rejectApplication(Long applicationId);
}
