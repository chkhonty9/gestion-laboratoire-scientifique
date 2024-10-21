package org.cn.userservice.dao;

import org.cn.userservice.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String email);
}
