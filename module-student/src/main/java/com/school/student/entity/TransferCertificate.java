package com.school.student.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "transfer_certificates", schema = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferCertificate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "certificate_number", nullable = false, unique = true)
    private String certificateNumber;
}
