package com.school.academic.entity;

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
@Table(name = "academic_sessions", schema = "academic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicSession extends BaseEntity {

    @Column(name = "year", nullable = false, unique = true)
    private String year; // e.g., "2026-2027"

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_current", nullable = false)
    private boolean current = false;
}
