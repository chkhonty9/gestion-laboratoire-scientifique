package org.cn.searchservice.dao;

import org.cn.searchservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByChercheurId(Long chercheurId);
    List<Project> findByEnseignantId(Long enseignantId);
    Project findByChercheurIdAndEnseignantId(Long chercheurId, Long enseignantId);
    List<Project> findByValideIsTrue();
    List<Project> findByValideIsTrueAndEnseignantId(Long enseignantId);
    List<Project> findByValideIsFalse();
    List<Project> findByValideIsFalseAndEnseignantId(Long enseignantId);
}
