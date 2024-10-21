package org.cn.userservice.entity;

import lombok.*;

import javax.persistence.*;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Enseignant extends Utilisateur {
    private String cne;
    private String thematicResearch;

}
