package com.campus.placement.controller;

import com.campus.placement.entity.ResumeFile;
import com.campus.placement.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/upload/{userId}")
    public ResumeFile upload(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        return resumeService.upload(userId, file);
    }

    @GetMapping("/user/{userId}")
    public ResumeFile getByUser(@PathVariable Long userId) {
        return resumeService.getByUser(userId);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws IOException {
        Path path = resumeService.getPath(fileName);
        Resource resource = new UrlResource(path.toUri());
        String ct = fileName.endsWith(".pdf") ? "application/pdf"
                : "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(ct))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
