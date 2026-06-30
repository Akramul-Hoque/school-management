package com.school.academic.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "teacher_subject_classes", schema = "academic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubjectClass extends BaseEntity {

    // In a microservices environment, this would just be Long staffId.
    // Here we can use the ID directly, or fetch from HrService.
    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession session;
}
