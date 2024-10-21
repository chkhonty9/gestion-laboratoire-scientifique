package org.cn.searchservice.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.cn.searchservice.model.Chercheur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@FeignClient(name = "USER-SERVICE")
public interface ChercheurRestClient {

    @GetMapping("/api/chercheurs/{id}")
    @CircuitBreaker(name = "chercheurService", fallbackMethod = "getChercheur")
    Chercheur getChercheur(@PathVariable Long id);

    default Chercheur getChercheur(Long id, Exception ex) {
        System.out.println("Fallback triggered for get chercheur: " + Arrays.toString(ex.getStackTrace()));

        return null;
    }

}
