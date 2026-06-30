package com.school.student.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "student_documents", schema = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDocument extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "document_type", nullable = false)
    private String documentType; // BIRTH_CERTIFICATE, PREVIOUS_REPORT_CARD

    @Column(name = "file_url", nullable = false)
    private String fileUrl;
}
