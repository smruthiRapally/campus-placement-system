package com.campus.placement.controller;

import com.campus.placement.entity.CompanyDrive;
import com.campus.placement.service.CompanyDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
@RequiredArgsConstructor
public class CompanyDriveController {
    private final CompanyDriveService companyDriveService;

    @GetMapping
    public List<CompanyDrive> getAll() { return companyDriveService.findAll(); }

    @GetMapping("/{id}")
    public CompanyDrive getById(@PathVariable Long id) { return companyDriveService.findById(id); }

    @PostMapping
    public CompanyDrive create(@RequestBody CompanyDrive drive) { return companyDriveService.save(drive); }

    @PutMapping("/{id}")
    public CompanyDrive update(@PathVariable Long id, @RequestBody CompanyDrive drive) {
        CompanyDrive existing = companyDriveService.findById(id);
        existing.setCompanyName(drive.getCompanyName());
        existing.setJobRole(drive.getJobRole());
        existing.setLocation(drive.getLocation());
        existing.setMinCgpa(drive.getMinCgpa());
        existing.setPackageValue(drive.getPackageValue());
        existing.setRequiredSkills(drive.getRequiredSkills());
        existing.setDriveDate(drive.getDriveDate());
        existing.setDriveTime(drive.getDriveTime());
        existing.setStatus(drive.getStatus());
        existing.setDescription(drive.getDescription());
        existing.setTotalVacancies(drive.getTotalVacancies());
        return companyDriveService.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyDriveService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
