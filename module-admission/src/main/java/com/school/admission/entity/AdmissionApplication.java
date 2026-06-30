package com.school.admission.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "admission_applications", schema = "admission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionApplication extends BaseEntity {

    @Column(name = "application_number", nullable = false, unique = true)
    private String applicationNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "applied_class_id", nullable = false)
    private Long appliedClassId;

    @Column(name = "applied_session_id", nullable = false)
    private Long appliedSessionId;

    @Column(name = "guardian_first_name", nullable = false)
    private String guardianFirstName;

    @Column(name = "guardian_last_name", nullable = false)
    private String guardianLastName;

    @Column(name = "guardian_email", nullable = false)
    private String guardianEmail;

    @Column(name = "guardian_phone", nullable = false)
    private String guardianPhone;

    @Column(name = "guardian_relation", nullable = false)
    private String guardianRelation;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, APPROVED, REJECTED
}
