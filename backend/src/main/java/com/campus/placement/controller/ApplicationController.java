package com.campus.placement.controller;

import com.campus.placement.dto.ApplicationRequest;
import com.campus.placement.entity.Application;
import com.campus.placement.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping
    public List<Application> getAll() { return applicationService.findAll(); }

    @GetMapping("/student/{studentId}")
    public List<Application> getByStudent(@PathVariable Long studentId) { return applicationService.findByStudentId(studentId); }

    @PostMapping
    public Application create(@RequestBody ApplicationRequest request) { return applicationService.create(request); }

    @PutMapping("/{id}")
    public Application update(@PathVariable Long id, @RequestBody ApplicationRequest request) { return applicationService.update(id, request); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
