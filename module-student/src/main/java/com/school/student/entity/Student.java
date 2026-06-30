package com.school.student.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "students", schema = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    // Academic mappings
    @Column(name = "current_class_id", nullable = false)
    private Long currentClassId;

    @Column(name = "current_section_id", nullable = false)
    private Long currentSectionId;

    @Column(name = "current_session_id", nullable = false)
    private Long currentSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;
}
