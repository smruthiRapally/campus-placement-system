package com.campus.placement.repository;

import com.campus.placement.entity.Application;
import com.campus.placement.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);
    List<Application> findByDriveId(Long driveId);
    boolean existsByStudentIdAndDriveId(Long studentId, Long driveId);
    long countByStatus(ApplicationStatus status);
}
