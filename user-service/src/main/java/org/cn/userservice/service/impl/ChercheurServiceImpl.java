package org.cn.userservice.service.impl;

import lombok.AllArgsConstructor;
import org.cn.userservice.dao.ChercheurRepository;
import org.cn.userservice.entity.Chercheur;
import org.cn.userservice.service.ChercheurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ChercheurServiceImpl implements ChercheurService {
    private ChercheurRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Chercheur save(Chercheur chercheur) {
        System.out.println("- service : save chercheur");
        chercheur.setPassword(passwordEncoder.encode(chercheur.getPassword()));
        return repository.save(chercheur);
    }

    @Override
    public List<Chercheur> findAll() {
        System.out.println("- service : find all chercheurs");
        return repository.findAll();
    }

    @Override
    public Chercheur findById(Long id) {
        System.out.println("- service : find chercheur by id");
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        System.out.println("- service : delete chercheur by id");
        repository.deleteById(id);
    }
}
