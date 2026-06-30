package com.school.student.repository;

import com.school.student.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long> {
}
