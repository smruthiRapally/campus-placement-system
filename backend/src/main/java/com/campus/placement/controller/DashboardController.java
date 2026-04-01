package com.campus.placement.controller;

import com.campus.placement.entity.ApplicationStatus;
import com.campus.placement.entity.Role;
import com.campus.placement.repository.ApplicationRepository;
import com.campus.placement.repository.CompanyDriveRepository;
import com.campus.placement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final UserRepository userRepository;
    private final CompanyDriveRepository companyDriveRepository;
    private final ApplicationRepository applicationRepository;

    @GetMapping
    public Map<String, Object> stats() {
        long totalStudents = userRepository.findByRole(Role.STUDENT).size();
        long totalDrives = companyDriveRepository.count();
        long totalApplications = applicationRepository.count();
        long totalSelected = applicationRepository.countByStatus(ApplicationStatus.SELECTED);
        long totalRejected = applicationRepository.countByStatus(ApplicationStatus.REJECTED);
        double placementRate = totalStudents > 0
                ? Math.round((totalSelected * 100.0 / totalStudents) * 100.0) / 100.0 : 0.0;

        // Per-drive stats
        List<Map<String, Object>> driveStats = new ArrayList<>();
        companyDriveRepository.findAll().forEach(drive -> {
            var apps = applicationRepository.findByDriveId(drive.getId());
            long sel = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.SELECTED).count();
            long rej = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.REJECTED).count();
            Map<String, Object> ds = new LinkedHashMap<>();
            ds.put("driveId", drive.getId());
            ds.put("companyName", drive.getCompanyName());
            ds.put("jobRole", drive.getJobRole());
            ds.put("driveDate", drive.getDriveDate());
            ds.put("status", drive.getStatus());
            ds.put("totalApplicants", apps.size());
            ds.put("selected", sel);
            ds.put("rejected", rej);
            driveStats.add(ds);
        });

        // Per-department stats
        Map<String, long[]> deptMap = new LinkedHashMap<>();
        applicationRepository.findAll().forEach(app -> {
            String dept = app.getStudent() != null ? app.getStudent().getDepartment() : null;
            if (dept == null) dept = "Unknown";
            deptMap.computeIfAbsent(dept, k -> new long[]{0, 0});
            deptMap.get(dept)[0]++;
            if (app.getStatus() == ApplicationStatus.SELECTED) deptMap.get(dept)[1]++;
        });
        List<Map<String, Object>> deptStats = new ArrayList<>();
        deptMap.forEach((dept, counts) -> {
            Map<String, Object> d = new LinkedHashMap<>();
            d.put("department", dept);
            d.put("totalApplications", counts[0]);
            d.put("totalPlaced", counts[1]);
            deptStats.add(d);
        });

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalStudents", totalStudents);
        result.put("totalDrives", totalDrives);
        result.put("totalApplications", totalApplications);
        result.put("totalSelected", totalSelected);
        result.put("totalRejected", totalRejected);
        result.put("placementRate", placementRate);
        result.put("driveStats", driveStats);
        result.put("departmentStats", deptStats);
        return result;
    }
}
