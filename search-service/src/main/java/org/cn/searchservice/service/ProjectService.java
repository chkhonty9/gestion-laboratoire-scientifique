package org.cn.searchservice.service;

import org.cn.searchservice.entity.Project;

import java.util.List;

public interface ProjectService {
    Project save(Project project);
    List<Project> findAll();
    Project findById(Long id);
    void deleteById(Long id);
    List<Project> findByEnseignant(Long enseignantId);
    List<Project> findByChercheur(Long chercheurId);
    Project findProject(Long enseignantId, Long chercheurId);
    List<Project> projectsValide();
    List<Project> projectsNotValide();
    List<Project> projectValide(Long enseignantId);
    List<Project> projectNotValide(Long enseignantId);

}
