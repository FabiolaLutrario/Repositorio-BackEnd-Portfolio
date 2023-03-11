package com.project.portfolio.repository;

import com.project.portfolio.entity.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExperienciaRepository extends JpaRepository<Experiencia, Integer> {

    Optional<Experiencia> findByCargo (String cargo);
    boolean existsByCargo(String cargo);
    boolean existsByEmpresa(String empresa);
    boolean existsByFechaInicio(LocalDate fechaInicio);
}
