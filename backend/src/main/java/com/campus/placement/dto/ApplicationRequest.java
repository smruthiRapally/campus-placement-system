package com.campus.placement.dto;

import com.campus.placement.entity.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationRequest {
    private Long studentId;
    private Long driveId;
    private ApplicationStatus status;
    private String feedback;
}
