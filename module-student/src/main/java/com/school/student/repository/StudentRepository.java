package com.school.student.repository;

import com.school.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByCurrentClassIdAndCurrentSectionId(Long classId, Long sectionId, Pageable pageable);
    List<Student> findByCurrentClassIdAndCurrentSessionId(Long classId, Long sessionId);
}
