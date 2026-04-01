package com.campus.placement.repository;

import com.campus.placement.entity.CompanyDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDriveRepository extends JpaRepository<CompanyDrive, Long> {}
