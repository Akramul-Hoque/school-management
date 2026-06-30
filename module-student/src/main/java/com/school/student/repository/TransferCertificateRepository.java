package com.school.student.repository;

import com.school.student.entity.TransferCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TransferCertificateRepository extends JpaRepository<TransferCertificate, Long> {
    Optional<TransferCertificate> findByStudentId(Long studentId);
}
