package com.school.hr.service.api;

import com.school.hr.dto.*;
import java.util.List;

public interface HrService {
    // Staff
    StaffDto getStaffById(Long id);
    List<StaffDto> getActiveTeachers();
    StaffDto onboardStaff(StaffDto staffDto);

    // Leave
    LeaveRequestDto submitLeaveRequest(LeaveRequestForm form);
    void approveLeave(Long leaveRequestId);
    LeaveBalanceDto getLeaveBalance(Long staffId, String leaveType);

    // Department & Designation
    List<DepartmentDto> getAllDepartments();
    DepartmentDto createDepartment(DepartmentDto dto);
    
    List<DesignationDto> getAllDesignations();
    DesignationDto createDesignation(DesignationDto dto);
}
