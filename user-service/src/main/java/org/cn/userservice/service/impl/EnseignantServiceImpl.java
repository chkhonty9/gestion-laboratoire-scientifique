package org.cn.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.cn.userservice.dao.EnseignantRepository;
import org.cn.userservice.entity.Enseignant;
import org.cn.userservice.service.EnseignantService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EnseignantServiceImpl implements EnseignantService {
    private EnseignantRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Enseignant getEnseignant(Long id) {
        System.out.println("- service : getEnseignant");
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Enseignant> getAllEnseignants() {
        System.out.println("- service : getAllEnseignants");
        return repository.findAll();
    }

    @Override
    public Enseignant save(Enseignant enseignant) {
        System.out.println("- service : save enseignant");
        enseignant.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        return repository.save(enseignant);
    }

    @Override
    public void delete(Long id) {
        System.out.println("- service : delete enseignant");
        repository.deleteById(id);
    }
}
