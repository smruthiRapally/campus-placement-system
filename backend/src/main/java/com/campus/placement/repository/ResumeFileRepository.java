package com.campus.placement.repository;

import com.campus.placement.entity.ResumeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResumeFileRepository extends JpaRepository<ResumeFile, Long> {
    Optional<ResumeFile> findByUserId(Long userId);
}
