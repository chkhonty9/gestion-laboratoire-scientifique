package org.cn.userservice.web;

import lombok.AllArgsConstructor;
import org.cn.userservice.entity.Enseignant;
import org.cn.userservice.service.EnseignantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@AllArgsConstructor
public class EnseignantController {
    private EnseignantService enseignantService;

    @GetMapping
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        System.out.println("- web : getAllEnseignants");
        return ResponseEntity.ok(enseignantService.getAllEnseignants());
    }

    @PostMapping
    public ResponseEntity<Enseignant> save(@RequestBody Enseignant enseignant) {
        System.out.println("- web : save enseignant");
        return ResponseEntity.ok(enseignantService.save(enseignant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> update(@PathVariable Long id, @RequestBody Enseignant enseignant) {
        System.out.println("- web : update enseignant");
        if(enseignantService.getEnseignant(id) == null)
            return ResponseEntity.notFound().build();
        enseignant.setId(id);
        return ResponseEntity.ok(enseignantService.save(enseignant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("- web : delete enseignant");
        if(enseignantService.getEnseignant(id) == null)
            return ResponseEntity.notFound().build();
        enseignantService.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignant(@PathVariable Long id) {
        System.out.println("- web : get Enseignant");
        if(enseignantService.getEnseignant(id) == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(enseignantService.getEnseignant(id));
    }
}
