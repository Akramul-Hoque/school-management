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
@Table(name = "sections", schema = "academic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name; // e.g., "A", "B", "Science"
}
