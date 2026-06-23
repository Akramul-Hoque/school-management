package com.school.auth.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffOnboardedEvent {
    private Long staffId;
    private String username;
    private String email;
    private String roleName;
}
