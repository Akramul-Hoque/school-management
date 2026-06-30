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
@Table(name = "classes", schema = "academic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name; // e.g., "Class 1", "Class 2"

    @Column(name = "numeric_value", nullable = false)
    private Integer numericValue; // e.g., 1, 2 for sorting/promotion logic
}
