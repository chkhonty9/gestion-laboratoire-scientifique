package org.cn.searchservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.cn.searchservice.model.Enseignant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@FeignClient(name = "SEARCH-SERVICE")
public interface EnseignantRestClient {
    @GetMapping("/api/enseignants/{id}")
    @CircuitBreaker(name = "enseignantService", fallbackMethod = "getEnseignant")
    Enseignant getEnseignant(@PathVariable Long id);

    default Enseignant getEnseignant(Long id, Exception ex) {
        //System.out.println("Fallback triggered for get Enseignant: " + ex.getMessage());
        System.out.println("Fallback triggered for get Enseignant: " + Arrays.toString(ex.getStackTrace()));

        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        enseignant.setNom("not available");
        enseignant.setPrenom("not available");
        enseignant.setEmail("not available");
        enseignant.setCne("not available");
        enseignant.setRole("not available");
        return enseignant;
    }

}
