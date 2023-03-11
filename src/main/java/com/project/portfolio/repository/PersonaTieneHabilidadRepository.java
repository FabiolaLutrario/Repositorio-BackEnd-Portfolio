package com.project.portfolio.repository;

import com.project.portfolio.entity.PersonaTieneHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaTieneHabilidadRepository extends JpaRepository <PersonaTieneHabilidad, Integer> {

    boolean existsByHabilidad(String habilidad);
    Optional<PersonaTieneHabilidad> findByHabilidad (String habilidad);
}
