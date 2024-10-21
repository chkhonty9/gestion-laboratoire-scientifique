package org.cn.searchservice.web;

import lombok.AllArgsConstructor;
import org.cn.searchservice.clients.ChercheurRestClient;
import org.cn.searchservice.clients.EnseignantRestClient;
import org.cn.searchservice.entity.Project;
import org.cn.searchservice.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;
    private ChercheurRestClient chercheurRestClient;
    private EnseignantRestClient enseignantRestClient;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        System.out.println("- web : get All Projects");
        List<Project> projects = projectService.findAll();
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        System.out.println("- web : create Project");
        Project savedProject = projectService.save(project);
        savedProject.setEnseignant(enseignantRestClient.getEnseignant(savedProject.getEnseignantId()));
        savedProject.setChercheur(chercheurRestClient.getChercheur(savedProject.getChercheurId()));
        return ResponseEntity.ok(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,@RequestBody Project project) {
        System.out.println("- web : update Project");
        if (projectService.findById(id) == null)
            return ResponseEntity.notFound().build();
        project.setId(id);
        Project savedProject = projectService.save(project);
        savedProject.setEnseignant(enseignantRestClient.getEnseignant(savedProject.getEnseignantId()));
        savedProject.setChercheur(chercheurRestClient.getChercheur(savedProject.getChercheurId()));
        return ResponseEntity.ok(savedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        System.out.println("- web : delete Project");
        if(projectService.findById(id) == null)
            return ResponseEntity.notFound().build();
        projectService.deleteById(id);
        return ResponseEntity.ok("deleted ^_^!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        System.out.println("- web : get Project");
        if(projectService.findById(id) == null)
            return ResponseEntity.notFound().build();
        Project project = projectService.findById(id);
        project.setEnseignant(enseignantRestClient.getEnseignant(project.getEnseignantId()));
        project.setChercheur(chercheurRestClient.getChercheur(project.getChercheurId()));
        return ResponseEntity.ok(project);

    }

    @GetMapping("/assign")
    public ResponseEntity<Project> assign(@RequestParam Long projectId, @RequestParam Long chercheurId) {
        System.out.println("- web : assign Project");
        if(projectService.findById(projectId) == null || chercheurId == null)
            return ResponseEntity.notFound().build();
        if(chercheurRestClient.getChercheur(chercheurId) == null)
            return ResponseEntity.notFound().build();
        Project project = projectService.findById(projectId);
        project.setChercheurId(chercheurId);
        project = projectService.save(project);
        project.setEnseignant(enseignantRestClient.getEnseignant(project.getEnseignantId()));
        project.setChercheur(chercheurRestClient.getChercheur(chercheurId));
        return ResponseEntity.ok(project);
    }

    @GetMapping("/chercheur/{chercheurId}")
    public ResponseEntity<List<Project>> byChercheur(@PathVariable Long chercheurId) {
        System.out.println("- web : by chercheur");

        if(chercheurRestClient.getChercheur(chercheurId) == null)
            return ResponseEntity.notFound().build();

        List<Project> projects = projectService.findByChercheur(chercheurId);
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);

        //return ResponseEntity.ok(projectService.findByChercheur(chercheurId));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Project>> byEnseignant(@PathVariable Long enseignantId) {
        System.out.println("- web :  by enseignant");

        if(enseignantRestClient.getEnseignant(enseignantId) == null)
            return ResponseEntity.notFound().build();

        List<Project> projects = projectService.findByEnseignant(enseignantId);
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);

        //return ResponseEntity.ok(projectService.findByEnseignant(enseignantId));
    }

    @GetMapping("/project")
    public ResponseEntity<Project> project(@RequestParam Long chercheurId, @RequestParam Long enseignantId) {
        System.out.println("- web : project");
        if(enseignantRestClient.getEnseignant(enseignantId) == null || chercheurRestClient.getChercheur(chercheurId) == null)
            return ResponseEntity.notFound().build();

        Project p = projectService.findProject(enseignantId, chercheurId);

        p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
        p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));

        return ResponseEntity.ok(p);

        //return ResponseEntity.ok(projectService.findProject(enseignantId, chercheurId));
    }

    @GetMapping("/validateProject/{id}")
    public ResponseEntity<Project> validate(@PathVariable Long id) {
        if(projectService.findById(id) == null)
            return ResponseEntity.notFound().build();
        Project project = projectService.findById(id);
        project.setValide(true);

        Project p = projectService.save(project);

        p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
        p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));

        return ResponseEntity.ok(p);
    }

    @GetMapping("/valides")
    public ResponseEntity<List<Project>> valides() {
        System.out.println("- web : valides");

        List<Project> projects = projectService.projectsValide();
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);

       // return ResponseEntity.ok(projectService.projectsValide());
    }

    @GetMapping("/notValides")
    public ResponseEntity<List<Project>> notValides() {
        System.out.println("- web : notValides");

        List<Project> projects = projectService.projectsNotValide();
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);
        //return ResponseEntity.ok(projectService.projectsNotValide());
    }

    @GetMapping("/valides/{enseignantId}")
    public ResponseEntity<List<Project>> valide(@PathVariable Long enseignantId) {
        System.out.println("- web : valide");

        List<Project> projects = projectService.projectValide(enseignantId);
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);

        //return ResponseEntity.ok(projectService.projectValide(enseignantId));
    }

    @GetMapping("/notValides/{enseignantId}")
    public ResponseEntity<List<Project>> notValide(@PathVariable Long enseignantId) {
        System.out.println("- web : valide");

        List<Project> projects = projectService.projectNotValide(enseignantId);
        projects.forEach(p->{
            p.setEnseignant(enseignantRestClient.getEnseignant(p.getEnseignantId()));
            p.setChercheur(chercheurRestClient.getChercheur(p.getChercheurId()));
        });
        return ResponseEntity.ok(projects);

        //return ResponseEntity.ok(projectService.projectNotValide(enseignantId));
    }

}
