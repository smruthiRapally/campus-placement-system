package com.campus.placement.controller;

import com.campus.placement.entity.Role;
import com.campus.placement.entity.User;
import com.campus.placement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() { return userService.findAll(); }

    @GetMapping("/students")
    public List<User> getStudents() {
        return userService.findAll().stream()
                .filter(u -> u.getRole() == Role.STUDENT)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) { return userService.findById(id); }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        User existing = userService.findById(id);
        existing.setFullName(user.getFullName());
        existing.setDepartment(user.getDepartment());
        existing.setPhone(user.getPhone());
        existing.setCgpa(user.getCgpa());
        existing.setGraduationYear(user.getGraduationYear());
        return userService.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
