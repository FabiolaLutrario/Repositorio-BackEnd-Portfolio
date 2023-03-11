package com.project.portfolio.security.repository;

import com.project.portfolio.security.entity.Rol;
import com.project.portfolio.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
