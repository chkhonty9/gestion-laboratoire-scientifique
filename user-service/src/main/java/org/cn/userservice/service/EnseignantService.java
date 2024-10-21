package org.cn.userservice.service;

import org.cn.userservice.entity.Enseignant;

import java.util.List;

public interface EnseignantService {
    Enseignant getEnseignant(Long id);
    List<Enseignant> getAllEnseignants();
    Enseignant save(Enseignant enseignant);
    void delete(Long id);
}
