package com.school.student.repository;

import com.school.student.entity.PromotionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRecordRepository extends JpaRepository<PromotionRecord, Long> {
}
