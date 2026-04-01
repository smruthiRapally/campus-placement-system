package com.campus.placement.service;

import com.campus.placement.dto.ApplicationRequest;
import com.campus.placement.entity.Application;
import com.campus.placement.entity.ApplicationStatus;
import com.campus.placement.repository.ApplicationRepository;
import com.campus.placement.repository.CompanyDriveRepository;
import com.campus.placement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final CompanyDriveRepository companyDriveRepository;

    public List<Application> findAll() { return applicationRepository.findAll(); }
    public List<Application> findByStudentId(Long studentId) { return applicationRepository.findByStudentId(studentId); }

    public Application create(ApplicationRequest request) {
        if (applicationRepository.existsByStudentIdAndDriveId(request.getStudentId(), request.getDriveId())) {
            throw new RuntimeException("Already applied to this drive");
        }
        var student = userRepository.findById(request.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        var drive = companyDriveRepository.findById(request.getDriveId()).orElseThrow(() -> new RuntimeException("Drive not found"));

        if ("COMPLETED".equals(drive.getStatus())) throw new RuntimeException("Drive is already completed");
        if (drive.getMinCgpa() != null && student.getCgpa() != null && student.getCgpa() < drive.getMinCgpa()) {
            throw new RuntimeException("CGPA does not meet the minimum requirement of " + drive.getMinCgpa());
        }

        Application application = Application.builder()
                .student(student).drive(drive)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();
        return applicationRepository.save(application);
    }

    public Application update(Long id, ApplicationRequest request) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        if (request.getStatus() != null) application.setStatus(request.getStatus());
        application.setFeedback(request.getFeedback());
        return applicationRepository.save(application);
    }

    public void delete(Long id) { applicationRepository.deleteById(id); }
}
