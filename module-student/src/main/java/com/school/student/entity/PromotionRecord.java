package com.school.student.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "promotion_records", schema = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "from_session_id", nullable = false)
    private Long fromSessionId;

    @Column(name = "to_session_id", nullable = false)
    private Long toSessionId;

    @Column(name = "from_class_id", nullable = false)
    private Long fromClassId;

    @Column(name = "to_class_id", nullable = false)
    private Long toClassId;

    @Column(name = "status", nullable = false)
    private String status; // PROMOTED, RETAINED
}
