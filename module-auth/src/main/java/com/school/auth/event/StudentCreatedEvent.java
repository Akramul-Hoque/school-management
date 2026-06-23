package com.school.auth.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreatedEvent {
    private Long studentId;
    private String username;
    private String email;
}
