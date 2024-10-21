package org.cn.searchservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cn.searchservice.model.Chercheur;
import org.cn.searchservice.model.Enseignant;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean valide;
    private Long chercheurId;
    private Long enseignantId;
    @Transient
    private Chercheur chercheur;
    @Transient
    private Enseignant enseignant;
}
