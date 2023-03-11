package com.project.portfolio.repository;

import com.project.portfolio.entity.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    Optional<Proyecto> findByNombre (String nombre);
    boolean existsByNombre(String nombre);
    boolean existsByFechaRealizacion(LocalDate fechaRealizacion);
    boolean existsByLink(String link);
}
