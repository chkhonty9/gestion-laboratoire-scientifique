package org.cn.userservice.entity;

import lombok.*;

import javax.persistence.Entity;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Chercheur extends Utilisateur {
    private String registrationNumber;

}
