package com.campus.placement.service;

import com.campus.placement.entity.ResumeFile;
import com.campus.placement.entity.User;
import com.campus.placement.repository.ResumeFileRepository;
import com.campus.placement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private final ResumeFileRepository resumeFileRepository;
    private final UserRepository userRepository;

    public ResumeFile upload(Long userId, MultipartFile file) throws IOException {
        String ct = file.getContentType();
        if (ct == null || (!ct.equals("application/pdf") &&
                !ct.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            throw new RuntimeException("Only PDF and DOCX files are allowed");
        }
        Path dir = Paths.get(uploadDir);
        if (!Files.exists(dir)) Files.createDirectories(dir);

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), dir.resolve(fileName));

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ResumeFile> existing = resumeFileRepository.findByUserId(userId);
        existing.ifPresent(r -> {
            try { Files.deleteIfExists(dir.resolve(r.getFileName())); } catch (IOException ignored) {}
            resumeFileRepository.delete(r);
        });

        return resumeFileRepository.save(ResumeFile.builder()
                .fileName(fileName).fileUrl("/api/resume/download/" + fileName)
                .contentType(ct).user(user).build());
    }

    public ResumeFile getByUser(Long userId) {
        return resumeFileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No resume found"));
    }

    public Path getPath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }
}
