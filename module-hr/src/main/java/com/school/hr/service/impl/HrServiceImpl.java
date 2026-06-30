package com.school.hr.service.impl;

import com.school.common.exception.BusinessRuleException;
import com.school.common.exception.ResourceNotFoundException;
import com.school.hr.dto.*;
import com.school.hr.entity.*;
import com.school.hr.event.LeaveApprovedEvent;
import com.school.hr.event.StaffOnboardedEvent;
import com.school.hr.mapper.*;
import com.school.hr.repository.*;
import com.school.hr.service.api.HrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HrServiceImpl implements HrService {

    private final StaffRepository staffRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    private final StaffMapper staffMapper;
    private final DepartmentMapper departmentMapper;
    private final DesignationMapper designationMapper;
    private final LeaveRequestMapper leaveRequestMapper;
    private final LeaveBalanceMapper leaveBalanceMapper;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public StaffDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        return staffMapper.toDto(staff);
    }

    @Override
    public List<StaffDto> getActiveTeachers() {
        return staffRepository.findByActiveTrue().stream()
                .filter(s -> s.getDesignation() != null && s.getDesignation().getName().equalsIgnoreCase("Teacher"))
                .map(staffMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StaffDto onboardStaff(StaffDto staffDto) {
        Department dept = departmentRepository.findById(staffDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Designation desig = designationRepository.findById(staffDto.getDesignationId())
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found"));

        Staff staff = staffMapper.toEntity(staffDto);
        staff.setDepartment(dept);
        staff.setDesignation(desig);
        if (staff.getDateOfJoining() == null) {
            staff.setDateOfJoining(LocalDate.now());
        }
        
        Staff saved = staffRepository.save(staff);

        // Publish event for Auth module to create portal login
        StaffOnboardedEvent event = new StaffOnboardedEvent(
                saved.getId(),
                saved.getStaffCode(),
                saved.getEmail(),
                desig.getName().equalsIgnoreCase("Teacher") ? "ROLE_TEACHER" : "ROLE_STAFF"
        );
        rabbitTemplate.convertAndSend("hr.exchange", "staff.onboarded", event);

        return staffMapper.toDto(saved);
    }

    @Override
    @Transactional
    public LeaveRequestDto submitLeaveRequest(LeaveRequestForm form) {
        Staff staff = staffRepository.findById(form.getStaffId())
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

        LeaveBalance balance = leaveBalanceRepository.findByStaffIdAndLeaveType(staff.getId(), form.getLeaveType())
                .orElseThrow(() -> new BusinessRuleException("Leave balance not configured for type: " + form.getLeaveType()));

        int requestedDays = form.getStartDate().until(form.getEndDate()).getDays() + 1;
        if (balance.getTotalDays() - balance.getUsedDays() < requestedDays) {
            throw new BusinessRuleException("Insufficient leave balance");
        }

        LeaveRequest request = new LeaveRequest();
        request.setStaff(staff);
        request.setLeaveType(form.getLeaveType());
        request.setStartDate(form.getStartDate());
        request.setEndDate(form.getEndDate());
        request.setReason(form.getReason());
        request.setStatus("PENDING");

        return leaveRequestMapper.toDto(leaveRequestRepository.save(request));
    }

    @Override
    @Transactional
    public void approveLeave(Long leaveRequestId) {
        LeaveRequest request = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new BusinessRuleException("Leave request is already " + request.getStatus());
        }

        LeaveBalance balance = leaveBalanceRepository.findByStaffIdAndLeaveType(request.getStaff().getId(), request.getLeaveType())
                .orElseThrow(() -> new BusinessRuleException("Leave balance not configured"));

        int days = request.getStartDate().until(request.getEndDate()).getDays() + 1;
        balance.setUsedDays(balance.getUsedDays() + days);
        leaveBalanceRepository.save(balance);

        request.setStatus("APPROVED");
        leaveRequestRepository.save(request);

        LeaveApprovedEvent event = new LeaveApprovedEvent(
                request.getStaff().getId(),
                request.getId(),
                request.getLeaveType(),
                request.getStartDate(),
                request.getEndDate()
        );
        rabbitTemplate.convertAndSend("hr.exchange", "leave.approved", event);
    }

    @Override
    public LeaveBalanceDto getLeaveBalance(Long staffId, String leaveType) {
        LeaveBalance balance = leaveBalanceRepository.findByStaffIdAndLeaveType(staffId, leaveType)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found"));
        return leaveBalanceMapper.toDto(balance);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DepartmentDto createDepartment(DepartmentDto dto) {
        Department dept = departmentMapper.toEntity(dto);
        return departmentMapper.toDto(departmentRepository.save(dept));
    }

    @Override
    public List<DesignationDto> getAllDesignations() {
        return designationRepository.findAll().stream().map(designationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DesignationDto createDesignation(DesignationDto dto) {
        Designation desig = designationMapper.toEntity(dto);
        return designationMapper.toDto(designationRepository.save(desig));
    }
}
