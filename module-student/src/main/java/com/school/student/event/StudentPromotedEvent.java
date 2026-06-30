package com.school.student.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPromotedEvent implements Serializable {
    private Long fromSessionId;
    private Long toSessionId;
    private Long classId;
}
