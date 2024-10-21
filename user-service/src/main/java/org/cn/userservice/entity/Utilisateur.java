package org.cn.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String role;

}
