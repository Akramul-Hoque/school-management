package com.school.academic.repository;

import com.school.academic.entity.RoutineEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoutineEntryRepository extends JpaRepository<RoutineEntry, Long> {
    List<RoutineEntry> findByClassEntityIdAndSectionIdAndSessionId(Long classId, Long sectionId, Long sessionId);
    List<RoutineEntry> findByStaffIdAndSessionId(Long staffId, Long sessionId);
}
