package com.campus.placement.controller;

import com.campus.placement.entity.Skill;
import com.campus.placement.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/user/{userId}")
    public List<Skill> getByUser(@PathVariable Long userId) { return skillService.findByUserId(userId); }

    @GetMapping
    public List<Skill> getAll() { return skillService.findAll(); }

    @PostMapping("/user/{userId}")
    public Skill create(@PathVariable Long userId, @RequestBody Skill skill) {
        return skillService.create(userId, skill);
    }

    @PutMapping("/{id}")
    public Skill update(@PathVariable Long id, @RequestBody Skill skill) {
        return skillService.update(id, skill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
