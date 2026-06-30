package com.school.academic.repository;

import com.school.academic.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEntityRepository extends JpaRepository<ClassEntity, Long> {
}
