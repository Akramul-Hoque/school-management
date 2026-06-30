package com.school.hr.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffOnboardedEvent implements Serializable {
    private Long staffId;
    private String username;
    private String email;
    private String roleName;
}
