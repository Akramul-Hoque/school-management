package com.school.cms.entity;

import com.school.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_submissions", schema = "cms")
@Getter
@Setter
@NoArgsConstructor
public class ContactSubmission extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "ip_address")
    private String ipAddress;
}
