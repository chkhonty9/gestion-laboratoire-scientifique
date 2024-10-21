package org.cn.userservice.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.AllArgsConstructor;
import org.cn.userservice.entity.Chercheur;
import org.cn.userservice.service.ChercheurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Gestion laboratoire scientifique",
                description = " un système \n" +
                        "d’information innovant destiné à optimiser la gestion de ses tâches administratives et de recherche.",
                version = "1.0.0"
        ),

        servers = @Server(
                url = "http://localhost:8081/"
        )
)

@RestController
@RequestMapping("/api/chercheurs")
@AllArgsConstructor
public class ChercheurController {
    private ChercheurService service;


    @GetMapping
    public ResponseEntity<List<Chercheur>> getAllChercheurs() {
        System.out.println("- web : getAllChercheurs");
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(
            summary = "Save Chercheur",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Chercheur.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Successfully saved Chercheur",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Chercheur.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<Chercheur> save(@RequestBody Chercheur chercheur) {
        System.out.println("- web : save Chercheur");
        return ResponseEntity.ok(service.save(chercheur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chercheur> update(@PathVariable Long id, @RequestBody Chercheur chercheur) {
        System.out.println("- web : update enseignant");
        if(service.findById(id) == null)
            return ResponseEntity.notFound().build();
        chercheur.setId(id);
        return ResponseEntity.ok(service.save(chercheur));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("- web : delete enseignant");
        if(service.findById(id) == null)
            return ResponseEntity.notFound().build();
        service.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "get chercheur by id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Chercheur.class))
                    ),
                    @ApiResponse(responseCode = "404",description = "not found ")
            }

    )
    public ResponseEntity<Chercheur> getChercheur(@PathVariable Long id) {
        System.out.println("- web : find chercheur By Id");
        if(service.findById(id) == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.findById(id));

    }
}
