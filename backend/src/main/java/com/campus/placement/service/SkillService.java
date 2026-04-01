package com.campus.placement.service;

import com.campus.placement.entity.Skill;
import com.campus.placement.entity.User;
import com.campus.placement.repository.SkillRepository;
import com.campus.placement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public List<Skill> findAll() { return skillRepository.findAll(); }

    public List<Skill> findByUserId(Long userId) { return skillRepository.findByUserId(userId); }

    public Skill create(Long userId, Skill skill) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        skill.setUser(user);
        return skillRepository.save(skill);
    }

    public Skill update(Long id, Skill updated) {
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new RuntimeException("Skill not found"));
        skill.setName(updated.getName());
        skill.setLevel(updated.getLevel());
        skill.setCertification(updated.getCertification());
        return skillRepository.save(skill);
    }

    public void delete(Long id) { skillRepository.deleteById(id); }
}
