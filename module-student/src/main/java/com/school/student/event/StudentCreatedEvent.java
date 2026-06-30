package com.school.student.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreatedEvent implements Serializable {
    private Long studentId;
    private String username;
    private String email;
}
