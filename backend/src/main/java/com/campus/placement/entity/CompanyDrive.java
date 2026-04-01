package com.campus.placement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "company_drives")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobRole;

    private String location;
    private Double minCgpa;
    private Double packageValue;
    private String requiredSkills;
    private LocalDate driveDate;
    private LocalTime driveTime;
    private String status; // UPCOMING, ONGOING, COMPLETED
    private String description;
    private Integer totalVacancies;
}
