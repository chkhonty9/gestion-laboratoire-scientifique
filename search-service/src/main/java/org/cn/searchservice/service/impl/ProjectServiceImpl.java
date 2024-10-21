package org.cn.searchservice.service.impl;

import lombok.AllArgsConstructor;
import org.cn.searchservice.dao.ProjectRepository;
import org.cn.searchservice.entity.Project;
import org.cn.searchservice.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository repository;

    @Override
    public Project save(Project project) {
        System.out.println("- service : Save Project  ");
        return repository.save(project);
    }

    @Override
    public List<Project> findAll() {
        System.out.println("- service : Find All Project  ");
        return repository.findAll();
    }

    @Override
    public Project findById(Long id) {
        System.out.println("- service : Find Project by id  ");
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("- service : Delete Project by id  ");
        repository.deleteById(id);
    }

    @Override
    public List<Project> findByEnseignant(Long enseignantId) {
        System.out.println("- service : Find Project by Enseignant id  ");
        return repository.findByEnseignantId(enseignantId);
    }

    @Override
    public List<Project> findByChercheur(Long chercheurId) {
        System.out.println("- service : Find Project by Chercheur id  ");
        return repository.findByChercheurId(chercheurId);
    }

    @Override
    public Project findProject(Long enseignantId, Long chercheurId) {
        System.out.println("- service : Find Project by Enseignant id  and chercheur id  ");
        return repository.findByChercheurIdAndEnseignantId(chercheurId, enseignantId);
    }

    @Override
    public List<Project> projectsValide() {
        System.out.println("- service : Validate Project  ");
        return repository.findByValideIsTrue();
    }

    @Override
    public List<Project> projectsNotValide() {
        System.out.println("- service : not Validate Project  ");
        return repository.findByValideIsFalse();
    }

    @Override
    public List<Project> projectValide(Long enseignantId) {
        System.out.println("- service : Valide Project  ");
        return repository.findByValideIsTrueAndEnseignantId(enseignantId);
    }

    @Override
    public List<Project> projectNotValide(Long enseignantId) {
        System.out.println("- service : not Valide Project  ");
        return repository.findByValideIsFalseAndEnseignantId(enseignantId);
    }

}
