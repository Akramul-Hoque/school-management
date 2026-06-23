package com.school.auth.event;

import com.school.auth.service.api.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventListener {

    private final AuthUserService authUserService;

    @RabbitListener(queues = "auth-staff-onboarded-queue")
    public void handleStaffOnboarded(StaffOnboardedEvent event) {
        log.info("Received StaffOnboardedEvent for staffId: {}, username: {}", event.getStaffId(), event.getUsername());
        try {
            authUserService.createPortalLogin(
                    event.getStaffId(),
                    event.getUsername(),
                    event.getEmail(),
                    event.getRoleName() != null ? event.getRoleName() : "ROLE_TEACHER"
            );
            log.info("Successfully created portal login for onboarded staff: {}", event.getUsername());
        } catch (Exception e) {
            log.error("Failed to create portal login for staff: {}", event.getUsername(), e);
        }
    }

    @RabbitListener(queues = "auth-student-created-queue")
    public void handleStudentCreated(StudentCreatedEvent event) {
        log.info("Received StudentCreatedEvent for studentId: {}, username: {}", event.getStudentId(), event.getUsername());
        try {
            authUserService.createPortalLogin(
                    event.getStudentId(),
                    event.getUsername(),
                    event.getEmail(),
                    "ROLE_STUDENT"
            );
            log.info("Successfully created portal login for student: {}", event.getUsername());
        } catch (Exception e) {
            log.error("Failed to create portal login for student: {}", event.getUsername(), e);
        }
    }
}
