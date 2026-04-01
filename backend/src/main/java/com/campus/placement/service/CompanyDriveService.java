package com.campus.placement.service;

import com.campus.placement.entity.CompanyDrive;
import com.campus.placement.repository.CompanyDriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyDriveService {
    private final CompanyDriveRepository companyDriveRepository;

    public List<CompanyDrive> findAll() { return companyDriveRepository.findAll(); }
    public CompanyDrive findById(Long id) { return companyDriveRepository.findById(id).orElseThrow(() -> new RuntimeException("Drive not found")); }
    public CompanyDrive save(CompanyDrive drive) { return companyDriveRepository.save(drive); }
    public void delete(Long id) { companyDriveRepository.deleteById(id); }
}
