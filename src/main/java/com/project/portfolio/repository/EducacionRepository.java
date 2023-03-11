package com.project.portfolio.repository;

import com.project.portfolio.entity.Educacion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Integer> {

    Optional<Educacion> findByTitulo (String titulo);
    boolean existsByTitulo(String titulo);
    boolean existsByCentroEducativo(String centroEducativo);
    boolean existsByFechaInicio(LocalDate fechaInicio);
}
