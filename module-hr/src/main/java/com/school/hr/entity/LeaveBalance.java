package com.school.hr.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "leave_balances", schema = "hr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "leave_type", nullable = false)
    private String leaveType; // CASUAL, SICK, EARNED

    @Column(name = "total_days", nullable = false)
    private int totalDays;

    @Column(name = "used_days", nullable = false)
    private int usedDays;
}
