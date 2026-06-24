package com.school.admission.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admission_applications", schema = "admission")
@Getter
@Setter
@NoArgsConstructor
public class AdmissionApplication extends BaseEntity {

    @Column(name = "applicant_name", nullable = false)
    private String applicantName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "class_applied_id")
    private Long classAppliedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
