package org.cn.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant {
    private Long id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String role;
    private String cne;
    private String thematicResearch;

}
