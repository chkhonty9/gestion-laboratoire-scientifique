package org.cn.userservice.dao;

import org.cn.userservice.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

}
