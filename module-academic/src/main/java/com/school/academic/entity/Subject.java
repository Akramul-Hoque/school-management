package com.school.academic.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "subjects", schema = "academic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name; // e.g., "Mathematics", "English"

    @Column(name = "code", nullable = false, unique = true)
    private String code; // e.g., "MATH101"

    @Column(name = "is_elective", nullable = false)
    private boolean elective = false;
}
