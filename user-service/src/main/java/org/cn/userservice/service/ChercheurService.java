package org.cn.userservice.service;

import org.cn.userservice.entity.Chercheur;
import java.util.List;

public interface ChercheurService {
    Chercheur save(Chercheur chercheur);
    List<Chercheur> findAll();
    Chercheur findById(Long id);
    void delete(Long id);
}
