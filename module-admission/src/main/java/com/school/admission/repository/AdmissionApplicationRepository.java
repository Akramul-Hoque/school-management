package com.school.admission.repository;

import com.school.admission.entity.AdmissionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Long> {
    Page<AdmissionApplication> findByStatus(String status, Pageable pageable);
}
