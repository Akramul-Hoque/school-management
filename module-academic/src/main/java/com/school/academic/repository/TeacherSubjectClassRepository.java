package com.school.academic.repository;

import com.school.academic.entity.TeacherSubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeacherSubjectClassRepository extends JpaRepository<TeacherSubjectClass, Long> {
    List<TeacherSubjectClass> findByStaffIdAndSessionId(Long staffId, Long sessionId);
    List<TeacherSubjectClass> findByClassEntityId(Long classId);
    boolean existsByClassEntityIdAndSectionId(Long classId, Long sectionId);
}
